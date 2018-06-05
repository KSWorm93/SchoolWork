/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashmap;

/**
 *
 * @author Tobias
 */
public interface Map<K, V>
{
   public V put(K key, V value);
   public V get(K key);
   public V remove(K key);
   public int size();
   public String getMapName();
   public default boolean isEmpty()
   {
       return size() == 0;
   }
}
