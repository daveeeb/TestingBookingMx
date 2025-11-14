import { Graph, validateGraphData, buildGraph, getNearbyCities, sampleData } from "../graph.js";

// --- Graph class tests ---
describe("Graph class", () => {
    test("adds cities correctly", () => {
        const g = new Graph();
        g.addCity("A");
        g.addCity("B");
        expect(g.adj.has("A")).toBe(true);
        expect(g.adj.has("B")).toBe(true);
    });

    test("throws error for invalid city name", () => {
        const g = new Graph();
        expect(() => g.addCity("")).toThrow();
        expect(() => g.addCity(null)).toThrow();
    });

    test("adds edges correctly", () => {
        const g = new Graph();
        g.addCity("A");
        g.addCity("B");
        g.addEdge("A", "B", 10);

        expect(g.neighbors("A")[0]).toEqual({ to: "B", distance: 10 });
        expect(g.neighbors("B")[0]).toEqual({ to: "A", distance: 10 });
    });

    test("throws error for edges referencing unknown cities", () => {
        const g = new Graph();
        g.addCity("A");

        expect(() => g.addEdge("A", "X", 10)).toThrow();
    });

    test("throws error for invalid distances", () => {
        const g = new Graph();
        g.addCity("A");
        g.addCity("B");

        expect(() => g.addEdge("A", "B", -2)).toThrow();
        expect(() => g.addEdge("A", "B", "bad")).toThrow();
    });
});

// --- validateGraphData tests ---
describe("validateGraphData", () => {
    test("returns ok=true for valid dataset", () => {
        const result = validateGraphData(sampleData);
        expect(result.ok).toBe(true);
    });

    test("fails if cities are duplicated", () => {
        const data = {
            cities: ["A", "A"],
            edges: []
        };
        expect(validateGraphData(data).ok).toBe(false);
    });

    test("fails if an edge references an unknown city", () => {
        const data = {
            cities: ["A"],
            edges: [{ from: "A", to: "B", distance: 10 }]
        };
        expect(validateGraphData(data).ok).toBe(false);
    });

    test("fails if distance is invalid", () => {
        const data = {
            cities: ["A", "B"],
            edges: [{ from: "A", to: "B", distance: -5 }]
        };
        expect(validateGraphData(data).ok).toBe(false);
    });
});

// --- buildGraph tests ---
describe("buildGraph", () => {
    test("builds a graph from dataset", () => {
        const g = buildGraph(sampleData.cities, sampleData.edges);
        expect(g.adj.size).toBe(sampleData.cities.length);
    });
});

// --- getNearbyCities tests ---
describe("getNearbyCities", () => {
    test("returns nearby cities sorted by distance", () => {
        const g = buildGraph(sampleData.cities, sampleData.edges);
        const result = getNearbyCities(g, "Guadalajara", 100);

        expect(result.length).toBeGreaterThan(0);
        expect(result[0].distance).toBeLessThanOrEqual(result[1]?.distance ?? Infinity);
    });

    test("returns an empty list if city does not exist", () => {
        const g = buildGraph(sampleData.cities, sampleData.edges);
        expect(getNearbyCities(g, "Unknown")).toEqual([]);
    });

    test("throws if graph is not a Graph instance", () => {
        expect(() => getNearbyCities({}, "A")).toThrow();
    });
});
