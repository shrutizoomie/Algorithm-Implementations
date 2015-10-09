package geeks.geeks.Trees;

import java.util.ArrayList;
import java.util.List;


/**
 * Inorder, PreOrder, Postorder traversal
 * @author shruti_sharma1
 *
 */

public class TreeTraversal
{
   // every tree node has to have the left, right node and the value field
   TreeTraversal left;
   TreeTraversal right;
   int value;

   static List<Integer> inOrderResult = new ArrayList<Integer>();

   static List<Integer> preOrderResult = new ArrayList<Integer>();

   static List<Integer> postOrderResult = new ArrayList<Integer>();

   public TreeTraversal(int nodeValue)
   {
      this.left = null;
      this.right = null;
      value = nodeValue;
   }
   
   public TreeTraversal getLeft()
   {
      return left;
   }
   
   public TreeTraversal getRight()
   {
      return right;
   }
   
   public int getValue()
   {
      return value;
   }
   
   /*************************************Problem 1 : Create a binary Tree*************************************************/
   
   /**
    * API to add a node to tree
    * @param num
    */
   public void addNode(int num)
   {
      // traverse the left subtree
      if (num < this.value) 
      {
         // recursively call addNode API till you reach the right position to insert
         if (this.left != null) 
         {
            this.left.addNode(num);
         } 
         else 
         {
            this.left = new TreeTraversal(num);
         }
      } 
      // traverse the right subtree
      else 
      {
         if (this.right != null)
         {
            this.right.addNode(num);
         } 
         else 
         {
            this.right = new TreeTraversal(num);
         }
      }
   }

   /************************************Problem 2 : Inorder Traversal **************************************************/

   /**
    * Inorder traversal of tree
    * @param root
    */
   public static void inOrderTraversal(TreeTraversal root)
   {
      if (root != null)
      {
         inOrderTraversal(root.getLeft());
         inOrderResult.add(root.value);
         inOrderTraversal(root.getRight());
      }
   }
   
   /******************************************Problem 4 : PreOrder Traversal ********************************************/
   /**
    * Pre order traversal of tree
    * @param root
    */
   public static void preOrderTraversal(TreeTraversal root)
   {
      if (root != null)
      {
         preOrderResult.add(root.value);
         preOrderTraversal(root.getLeft());
         preOrderTraversal(root.getRight());
      }
   }
   
   /******************************************Problem 5 : PostOrder Traversal ********************************************/

   /**
    * Postorder traversal of tree
    * @param root
    */
   public static void postOrderTraversal(TreeTraversal root)
   {
      if (root != null)
      {
         postOrderTraversal(root.getLeft());
         postOrderTraversal(root.getRight());
         postOrderResult.add(root.value);
      }
   }
   
   /******************************************Problem 6 : Search Node Recursive ********************************************/

   /**
    * API to find a node in a tree : Recursive
    * @param root
    * @param value
    * @return
    */
   public static TreeTraversal findNode(TreeTraversal root, int value)
   {
         if ((root == null) || (root.getValue() == value))
         {
            return root;
         }
         // invoke findNode recursively till root == null or node found
         else if (value < root.getValue())
         {
            return findNode(root.getLeft(), value);
         }
         else
         {
            return findNode(root.getRight(), value);
         }

   }
   
   /******************************************Problem 7 : Search Node Iterative ********************************************/

   /**
    * API to search for a node in a tree : Iterate till root becomes null
    * @param root
    * @param value
    * @return
    */
   public static TreeTraversal searchNode(TreeTraversal root, int value)
   {
      // update value of the root node and iterate till root node is not null / value not found
      while (root != null)
      {
         if (root.getValue() == value)
         {
            break;
         }
         if (value < root.getValue())
         {
            root = root.getLeft();
         }
         else
         {
            root = root.getRight();
         }
      }
      return root;
   }

   /******************************************Problem 8 : Height of a tree ********************************************/

   /**
    * API to find the height of a tree
    * @param root
    * @return
    */
   public static int heightOfTree(TreeTraversal root)
   {
      int height = 0;

      // height == 0
      if (root == null)
      {
         return height;
      }
      
      // height = 1 for the root node + Max height (left subtree height, right subtree height)
      
      return 1 + Math.max(heightOfTree(root.getLeft()), heightOfTree(root.getRight()));
   }
   
   /******************************************Problem 9 : Lowest Common Ancestor in a tree ********************************************/

   /**
    * APi to find the lowest common ancestor of 2 nodes
    * it is the node whose value is between the values of the left and right subchild
    * @param args
    */
   public static TreeTraversal lowestCommAncestor(TreeTraversal root, int nodeA, int nodeB)
   {
      if (root == null)
      {
         return null;
      }
      
      if ((nodeA < root.getValue()) && (nodeB < root.getValue()))
      {
         // traverse left subtree
         return lowestCommAncestor(root.getLeft(), nodeA, nodeB);
      }
      else if ((nodeA > root.getValue()) && (nodeB > root.getValue()))
      {
      // traverse right subtree
         return lowestCommAncestor(root.getRight(), nodeA, nodeB);
      }
      // value of the node is between the values of nodeA and nodeB
      else
      {
         return root;
      }
   }
   
   /******************************************Problem 10 : Minimum node in a tree ********************************************/

   
   /**
    * get the minimum element in the tree : Leftmost element in the tree is the min element
    * @param args
    */
   public static TreeTraversal getMin(TreeTraversal root)
   {
      
      while (root.getLeft() != null)
      {
         root = root.getLeft();
      }
      return root;
   }
   
   /******************************************Problem 11 : Maximum node in a tree ********************************************/

   /**
    * Get the max element in a tree : Right most element in the tree is the max element in the tree
    * @param args
    */
   public static TreeTraversal getMax(TreeTraversal root)
   {
      while (root.getRight() != null)
      {
         root = root.getRight();
      }
      return root;
   }
   
   /******************************************Problem 12 : Size of a tree ********************************************/

   /**
    * Size of a tree is the number of elements present in the tree
    * @param root
    * @return
    */
   public static int sizeOfTree(TreeTraversal root)
   {
      if (root == null)
      {
         return 0;
      }
      return (1 + sizeOfTree(root.getLeft()) + sizeOfTree(root.getRight()));
   }

   /******************************************Problem 13 : Are 2 trees identical ********************************************/

   /**
    * API to check if 2 trees are identical. They are identical if they have the same structure and also
    * the nodes have the same values
    * @param root1
    * @param root2
    * @return
    */
   public static boolean identicalTrees(TreeTraversal root1, TreeTraversal root2)
   {
      boolean result = false;
      
      // if both trees are null return true
      if ((root1 == null) && (root2 == null))
      {
         return true;
      }
      
      // if both are not null we can continue comparing
      if ((root1 != null) && (root2 != null))
      {
      // check the node values, left and right subtrees recursively
         return  ((root1.value == root2.value)
               && (identicalTrees(root1.getLeft(), root2.getLeft()))
               && (identicalTrees(root1.getRight(), root2.getRight())));
      }
      
      return result;
   }
   
   /******************************************Problem 14 : Convert a Tree to its mirror image ********************************************/

   public static TreeTraversal convertTreeToItsMirrorImage(TreeTraversal root)
   {
      if (root == null)
      {
         return root;
      }
      else
      {
         convertTreeToItsMirrorImage(root.getLeft());
         convertTreeToItsMirrorImage(root.getRight());
         
         TreeTraversal temp = new TreeTraversal(0);
         temp = root.getLeft();
         root.left = root.right;
         root.right = temp;
         
      }
      return root;
   }

  /**********************************Problem 15 : Print all paths from root - leaf in a tree*****************************/
   
   /** 
   Given a binary tree, prints out all of its root-to-leaf 
   paths, one per line. Uses a recursive helper to do the work. 
  */ 
  public static void printPaths(TreeTraversal root) 
  { 
     int[] path = new int[1000]; 
     printPaths(root, path, 0); 
  }
  
  /** 
   Recursive printPaths helper -- given a node, and an array containing 
   the path from the root node up to but not including this node, 
   prints out all the root-leaf paths. 
  */ 
  public static void printPaths(TreeTraversal node, int[] path, int pathLen) 
  { 
    if (node==null) return;

    // append this node to the path array 
    path[pathLen] = node.value; 
    pathLen++;

    // it's a leaf, so print the path that led to here 
    if (node.left==null && node.right==null) { 
      printArray(path, pathLen); 
    } 
    else { 
    // otherwise try both subtrees 
      printPaths(node.left, path, pathLen); 
      printPaths(node.right, path, pathLen); 
    } 
  }

  /** 
   Utility that prints ints from an array on one line. 
  */ 
  private static void printArray(int[] ints, int len) { 
    int i; 
    for (i=0; i<len; i++) { 
     System.out.print(ints[i] + " "); 
    } 
    System.out.println(); 
  } 

  /**********************************Problem 16 : BFS traversal *****************************/
  
  /**
   * public void bfs()
   {
      // BFS uses Queue data structure

      Queue q = new LinkedList();

      q.add(rootNode);
      visited[rootNode] = true;

      printNode(rootNode);

      while( !q.isEmpty() )
      {
         int n, child;

         n = (q.peek()).intValue();

         child = getUnvisitedChildNode(n);    // Returns -1 if no unvisited niode left     

         if ( child != -1 )
         {  // Found an unvisted node 

            visited[child] = true;        // Mark as visited

            printNode(child);

            q.add(child);      // Add to queue 
         }
         else
         {
            q.remove();                  // Process next node
         }
      }
   }
   */
  
  /**********************************Problem 17 : Count leaf nodes in a tree*****************************/
  public static int countLeafNodes(TreeTraversal root)
  {
     int count = 0;
     
     if (root == null)
     {
        return count;
     }
     
     else if ((root.getLeft() == null) && (root.getRight() == null))
     {
        return count + 1;
     }
     else
     {
        return countLeafNodes(root.getLeft()) + countLeafNodes(root.getRight());
     }
  }

  /**********************************Problem 18 : Find if a tree is a Binary Search tree or not*****************************/
  /**
   *   1) Do In-Order Traversal of the given tree and store the result in a temp array.
       3) Check if the temp array is sorted in ascending order, if it is, then the tree is BST.
   */
  public static boolean isBST(TreeTraversal root)
  {
     inOrderTraversal(root);

     for(int i = 1; i < inOrderResult.size(); i++)
     {
        if (inOrderResult.get(i) < inOrderResult.get(i - 1))
        {
           return false;
        }
     }
     
     return true;
  }

  /**********************************Problem 19 : Check the child sum property in the node*****************************/
  /**
   * The sum of left ad right child nodes = the value of the parent node
   * @param args
   */
   public static boolean childSumProperty(TreeTraversal root)
   {
      int leftData = 0;
      int rightData = 0;
      if ((root == null) || (root.getLeft() == null && root.getRight() == null))
      {
         return true;
      }
      
      if (root.getLeft() != null)
         leftData = root.getLeft().getValue();
      
      if (root.getRight() != null)
         rightData = root.getRight().getValue();
      
      return ((root.getValue() == (leftData + rightData))
            && childSumProperty(root.getLeft()) 
            && childSumProperty(root.getRight()));
   }
  
   /**********************************Problem 20 : Diameter of a tree*****************************/
  
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      TreeTraversal tree = new TreeTraversal( 20 );
      TreeTraversal tree1 = new TreeTraversal( 20 );
      TreeTraversal tree2 = new TreeTraversal( 20 );
      
      int[] nums = {5, 4,2, 1, 0, 3, 7, 6, 9, 8, 10};
      int[] nums1 = {5, 4,2, 1, 0, 3, 7, 6, 9, 8};
      
      for(int i : nums ) 
      {
         tree.addNode( i );
         tree1.addNode(i);
         
      }
      
      for(int i : nums1 ) 
      {
         tree2.addNode(i);
         
      }
      
      boolean result = childSumProperty(tree);
      System.out.println(result);
      
//      boolean result = isBST(tree);
//      System.out.println(result);
//      
////      int count = countLeafNodes(tree);
//      System.out.println("Number of leaf nodes in the tree : " + count);
//       //printPaths(tree);
//      TreeTraversal result =  convertTreeToItsMirrorImage(tree);
//      
//      inOrderTraversal(result);
//    
//      for (Integer finalResult : inOrderResult)
//      {
//         System.out.print(finalResult.intValue() + "\t");
//      }
//      System.out.println("\n");
      
      
//      boolean result = identicalTrees(tree, tree1);
//      boolean result1 = identicalTrees(null, null);
//      boolean result2 = identicalTrees(tree, null);
//      boolean result3 = identicalTrees(null, tree1);
//      boolean result4 = identicalTrees(tree1, tree2);
//      
//      System.out.println(result);
//      System.out.println(result1);
//      System.out.println(result2);
//      System.out.println(result3);
//      System.out.println(result4);

      
//      int sizeTree = sizeOfTree(tree);
//      System.out.println("Size of tree is a tree is  : " + sizeTree);
//    
//      TreeTraversal minElement = getMin(tree);
//      System.out.println("Min element in a tree is  : " + minElement.getValue());
//      
//      TreeTraversal maxElement = getMax(tree);
//      System.out.println("Max element in a tree is  : " + maxElement.getValue());
//      
//      
//      TreeTraversal result = findNode(tree, 1);
//      
//      System.out.println("Result is : " + result.value);
//      
//      TreeTraversal result1 = searchNode(tree, 1);
//      
//      System.out.println("Result of search tree is : " + result1.value);
//      
//      
//      int height = heightOfTree(tree);
//      
//      System.out.println("Result of height of tree is : " + height);
//      
//      
//      TreeTraversal lca = lowestCommAncestor(tree, 1, 3);
//      
//      System.out.println("LCA of search tree is : " + lca.getValue());
//      
//      
//      inOrderTraversal(tree);
//      
//      for (Integer finalResult : inOrderResult)
//      {
//         System.out.print(finalResult.intValue() + "\t");
//      }
//      System.out.println("\n");
//      
//      preOrderTraversal(tree);
//      
//      for (Integer finalResult : preOrderResult)
//      {
//         System.out.print(finalResult.intValue() + "\t");
//      }
//      System.out.println("\n");
//      
//      postOrderTraversal(tree);
//      
//      for (Integer finalResult : postOrderResult)
//      {
//         System.out.println(finalResult.intValue() + "\t");
//      }
//      System.out.println("\n");
   }

}
