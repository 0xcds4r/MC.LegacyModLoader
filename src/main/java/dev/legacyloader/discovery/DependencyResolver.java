package dev.legacyloader.discovery;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Builds a deterministic load order from {@code required-*} / {@code after} /
 * {@code before} declarations. Missing required dependencies disable the
 * dependent mod instead of failing the whole boot; a cycle is reported and the
 * affected mods are disabled.
 */
public final class DependencyResolver {
    public record Resolution(List<DiscoveredMod> ordered, List<DiscoveredMod> disabled,
                             List<Diagnostic> diagnostics) {
    }

    private static final Set<String> BUILTIN = Set.of("minecraft", "forge", "fml", "java");

    public Resolution resolve(List<DiscoveredMod> discovered, Set<String> providedMods) {
        List<Diagnostic> diagnostics = new ArrayList<>();
        Map<String, DiscoveredMod> byId = new LinkedHashMap<>();

        for (DiscoveredMod mod : discovered) {
            DiscoveredMod previous = byId.putIfAbsent(mod.modId(), mod);
            if (previous != null) {
                mod.disable("duplicate mod id, already provided by " + previous.jarPath().getFileName());
                diagnostics.add(Diagnostic.error("Duplicate mod id '" + mod.modId() + "' in "
                        + mod.jarPath().getFileName() + "; disabled"));
            }
        }

        // Propagate missing/disabled required dependencies to a fixed point.
        boolean changed = true;
        while (changed) {
            changed = false;
            for (DiscoveredMod mod : byId.values()) {
                if (!mod.isEnabled()) {
                    continue;
                }
                for (String dependencyId : mod.dependencies().required()) {
                    if (BUILTIN.contains(dependencyId)) {
                        continue;
                    }
                    DiscoveredMod dependency = byId.get(dependencyId);
                    if (dependency == null) {
                        if (providedMods.contains(dependencyId)) {
                            diagnostics.add(Diagnostic.warn(mod.modId()
                                    + " depends on '" + dependencyId
                                    + "', which is provided by the compatibility layer (API-only)"));
                            continue;
                        }
                        mod.disable("missing required dependency: " + dependencyId);
                        diagnostics.add(Diagnostic.error(mod.modId() + " disabled: missing required dependency '"
                                + dependencyId + "'"));
                        changed = true;
                        break;
                    }
                    if (!dependency.isEnabled()) {
                        mod.disable("required dependency is disabled: " + dependencyId);
                        diagnostics.add(Diagnostic.error(mod.modId() + " disabled: dependency '"
                                + dependencyId + "' is disabled"));
                        changed = true;
                        break;
                    }
                }
            }
        }

        Map<String, Set<String>> outgoing = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        for (DiscoveredMod mod : byId.values()) {
            if (!mod.isEnabled()) {
                continue;
            }
            outgoing.computeIfAbsent(mod.modId(), ignored -> new LinkedHashSet<>());
            indegree.putIfAbsent(mod.modId(), 0);
        }

        for (DiscoveredMod mod : byId.values()) {
            if (!mod.isEnabled()) {
                continue;
            }
            Set<String> beforeMe = new LinkedHashSet<>(mod.dependencies().required());
            beforeMe.addAll(mod.dependencies().after());
            for (String dependencyId : beforeMe) {
                if (byId.containsKey(dependencyId) && byId.get(dependencyId).isEnabled()
                        && !dependencyId.equals(mod.modId())) {
                    addEdge(dependencyId, mod.modId(), outgoing, indegree);
                }
            }
            for (String targetId : mod.dependencies().before()) {
                if (byId.containsKey(targetId) && byId.get(targetId).isEnabled()
                        && !targetId.equals(mod.modId())) {
                    addEdge(mod.modId(), targetId, outgoing, indegree);
                }
            }
        }

        PriorityQueue<String> ready = new PriorityQueue<>(Comparator.naturalOrder());
        for (Map.Entry<String, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                ready.add(entry.getKey());
            }
        }

        List<DiscoveredMod> ordered = new ArrayList<>();
        while (!ready.isEmpty()) {
            String id = ready.poll();
            ordered.add(byId.get(id));
            for (String next : outgoing.getOrDefault(id, Set.of())) {
                int remaining = indegree.merge(next, -1, Integer::sum);
                if (remaining == 0) {
                    ready.add(next);
                }
            }
        }

        List<DiscoveredMod> disabled = new ArrayList<>();
        for (DiscoveredMod mod : byId.values()) {
            if (!mod.isEnabled()) {
                disabled.add(mod);
            }
        }

        if (ordered.size() < indegree.size()) {
            Set<String> emitted = new HashSet<>();
            for (DiscoveredMod mod : ordered) {
                emitted.add(mod.modId());
            }
            for (String id : indegree.keySet()) {
                if (!emitted.contains(id)) {
                    DiscoveredMod mod = byId.get(id);
                    mod.disable("dependency cycle involving: " + String.join(", ", findCycle(id, outgoing)));
                    disabled.add(mod);
                    diagnostics.add(Diagnostic.error(mod.modId() + " disabled: dependency cycle"));
                }
            }
        }

        diagnostics.add(Diagnostic.info("Load order: " + ordered.stream()
                .map(DiscoveredMod::modId).reduce((a, b) -> a + " -> " + b).orElse("<empty>")));
        return new Resolution(ordered, disabled, diagnostics);
    }

    private static void addEdge(String from, String to, Map<String, Set<String>> outgoing,
                                Map<String, Integer> indegree) {
        if (outgoing.computeIfAbsent(from, ignored -> new LinkedHashSet<>()).add(to)) {
            indegree.merge(to, 1, Integer::sum);
        }
    }

    private static List<String> findCycle(String start, Map<String, Set<String>> outgoing) {
        List<String> path = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!seen.add(current)) {
                continue;
            }
            path.add(current);
            for (String next : outgoing.getOrDefault(current, Set.of())) {
                stack.push(next);
            }
            if (path.size() > 16) {
                break;
            }
        }
        return path;
    }
}
