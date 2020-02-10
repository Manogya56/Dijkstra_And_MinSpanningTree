import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class Graph {
	

    public static int MINIMUM_SPANNING_TREE_ALGORITHM = 0;
    private HashSet<HashSet<String>> nodes = new HashSet<>();

    private HashSet<HashSet<String>> kUniversal = new HashSet<>();
    private ArrayList<Edge> kEdges = new ArrayList<>();
    private HashSet<String> sSet1, sSet2;
    private Integer totalCost;

    private HashSet<HashSet<String>> pUniversal = new HashSet<>();
    private ArrayList<Edge> pEdges = new ArrayList<>();
    private ArrayList<Edge> tempEdges = new ArrayList<>();
    private ArrayList<Edge> oEdges;
    private String selectNode = "";

    public void addEdge(String source, String des, Integer cost) {
        nodes.add(Sets.newHashSet(source));
        nodes.add(Sets.newHashSet(des));
        switch (MINIMUM_SPANNING_TREE_ALGORITHM) {
            case 0:
                kEdges.add(new Edge(source, des, cost));
                break;
            case 1:
                pEdges.add(new Edge(source, des, cost));
                selectNode = source;
                break;
        }
    }

    public ArrayList<Edge> kruskalMST() {
        totalCost = 0;
        kUniversal = new HashSet<>(nodes);
        int edgesLimit = kUniversal.size() - 1;
        ArrayList<Edge> MSTEdges = new ArrayList<>();
        kEdges.sort(Comparator.comparing(edge -> edge.cost));
        for (Edge edge : kEdges) {
            if (MSTEdges.size() == edgesLimit) break;
            if (isAcyclic(edge.source, edge.des, true)) {
                kUniversal.add(Sets.newHashSet(Sets.union(sSet1, sSet2)));
                kUniversal.remove(sSet1);
                kUniversal.remove(sSet2);
                MSTEdges.add(edge);
                totalCost += edge.cost;
            }
        }
        return MSTEdges;
    }

    public ArrayList<Edge> primMST() {
        totalCost = 0;
        pUniversal = new HashSet<>(nodes);
        oEdges = new ArrayList<>();
        ArrayList<Edge> MSTEdges = new ArrayList<>();
        int edgesLimit = pUniversal.size() - 1;
        tempEdges.addAll(pEdges);
        while (MSTEdges.size() != edgesLimit) {
            getSelectedEdges();
            for (int i = 0; i < oEdges.size(); i++) {
                Edge edge = oEdges.get(i);
                if (isAcyclic(edge.source, edge.des, false)) {
                    pUniversal.add(Sets.newHashSet(Sets.union(sSet1, sSet2)));
                    pUniversal.remove(sSet1);
                    pUniversal.remove(sSet2);
                    MSTEdges.add(edge);
                    totalCost += edge.cost;
                    selectNode = edge.des;
                    oEdges.remove(edge);
                    break;
                }
            }
        }
        return MSTEdges;
    }

    private void getSelectedEdges() {
        for (int i = 0; i < tempEdges.size(); i++) {
            Edge target = tempEdges.get(i);
            if (target.source.equals(selectNode)) {
                oEdges.add(target);
            } else if (target.des.equals(selectNode)) {
                String des = target.source;
                target.source = target.des;
                target.des = des;
                oEdges.add(target);
            }
        }
        tempEdges.removeAll(oEdges);
        oEdges.sort(Comparator.comparing(edge -> edge.cost));
    }

    private boolean isAcyclic(String srcAlias, String destAlias, boolean mode) {
        sSet1 = new HashSet<>();
        sSet2 = new HashSet<>();
        HashSet<HashSet<String>> targetSet;
        targetSet = mode ? kUniversal : pUniversal;
        for (HashSet<String> subSet : targetSet) {
            if (subSet.contains(srcAlias)) sSet1 = subSet;
            if (subSet.contains(destAlias)) sSet2 = subSet;
            if (sSet1 == sSet2) return false;
        }
        return true;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

}
