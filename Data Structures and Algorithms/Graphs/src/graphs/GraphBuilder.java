/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

/**
 *
 * @author Tobias
 */
public interface GraphBuilder
{
    public BuildNode createNode(String name);
    public void createEdge(BuildNode begin, BuildNode end);
    public Graph build();
}
