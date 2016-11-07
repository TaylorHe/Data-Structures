//Taylor He
//I pledge my honor that I have abided by the Stevens Honor System.

package homework4;
import java.util.Random;

public class Treap<E extends Comparable<E>> {
	private static class Node<E>{
		public E data;  			// key for search
		public int priority;  		// random heap priority
		public Node<E> left, right;	// left and right children

		/**
		 * Creates a node with left and right pointers null.
		 * If data is null, throws exception.
		 * 
		 * @param data		Data of the node
		 * @param priority	Priority of the node
		 */
		public Node(E data, int priority){
			//data can't be null
			if(data == null){
				throw new NullPointerException();
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		/**
		 * Rotates node and its parent to the right
		 * 
		 * @return void
		 */
		public void rotateRight(){
			/*       A            B   
			 *    B     E  ->  C     A
			 *  C   D              D   E
			 */

			//store values of A B C D E
			Node<E> A = this;
			Node<E> B = A.left;
			Node<E> C = null;
			Node<E> D = null;
			if(B != null){
				C = B.left;
				D = B.right;
			}
			Node<E> E = A.right;

			//swap A.data and B.data
			E temp = A.data;
			if(B != null){
				A.data = B.data;
				B.data = temp;
			}
			//swap A.priority and B.priority
			int temp2;
			if(A != null && B != null){
				temp2 = A.priority;
				A.priority = B.priority;
				B.priority = temp2;
			}
			
			//Fix tree
			if (B != null){
				A.right = B;
				B.right = E;
				B.left = D;
				A.left = C;
			}

		}

		/**
		 * Rotates node and its parent to the left
		 * @return void
		 */
		public void rotateLeft(){
			/*		A                B
			 *    E    B    ->    A     C
			 *		 D   C		E   D
			 */

			//store values of A B C D E
			Node<E> A = this;
			Node<E> B = A.right;
			Node<E> C = null;
			Node<E> D = null;
			if(B != null){
				C = B.right;
				D = B.left;
			}
			Node<E> E = A.left;

			//swap A and B's data
			E temp;
			temp = A.data;
			A.data = B.data;
			B.data = temp;
			//swap A and B's priorities
			int temp2;
			temp2 = A.priority;
			A.priority = B.priority;
			B.priority = temp2;

			//Fix tree
			A.left = B;
			B.left = E;
			B.right = D;
			A.right = C;
		}
	}
	
	
	private Random priorityGenerator;	//Random priority generator
	private Node<E> root;				//Root of treap
	
	/**
	 * Creates a treap with null root.
	 */
	public Treap(){
		root = null;
		priorityGenerator = new Random();
	}
	/**
	 * Creates a treap with null root and priority limit
	 * 
	 * @param seed
	 */
	public Treap(long seed){
		root = null;
		priorityGenerator = new Random(seed);
	}
	
	boolean addReturn = false;		//switches to true if add is successful
	/**
	 * Inserts given element into the tree, containing
	 * key as its data and a random priority generated
	 * by priorityGenerator.
	 * Returns true if successful in adding, false if
	 * there is a node with the same key in treap.
	 * 
	 * @param  key		The data of the node
	 * @return boolean	true if successful, false otherwise
	 */
	public boolean add(E key){
		add(root, key, priorityGenerator.nextInt());
		return addReturn;
	}
	/**
	 * Add function with given priority to make 
	 * it easier to test code
	 * @param  key 		data
	 * @param  p 		priority
	 * @return boolean 	true if added, false if error
	 */
	public boolean add(E key, int p){
		if(root == null)
			root = new Node<E>(key, p);
		else
			add(root, key, p);
		return addReturn;
	}
	/**
	 * Recursive helper add function
	 * 
	 * @post   The data field addReturn is set true if the item is added to the
	 *         tree, false if the item is already in the treap.
	 * @param  localRoot 	The local root of the subtree
	 * @param  key 			The data of the object to be inserted
	 * @return Node<E>		The new local root that now contains the inserted item
	 */
	private Node<E> add(Node<E> localRoot, E key, int p) {
		//if null, add and return true
		if (localRoot == null) {
			localRoot = new Node<E>(key, p);
			addReturn = true;
			return new Node<E>(key, p);
		//if repeated data, return false
		} else if (key.compareTo(localRoot.data) == 0) {
			addReturn = false;
			return localRoot;
		//if key < localRoot, recurse to the left
		} else if (key.compareTo(localRoot.data) < 0) {
			localRoot.left = add(localRoot.left, key, p);
			//Make sure to fix tree!
			if(localRoot.left.priority > localRoot.priority){
				localRoot.rotateRight();
			}
			return localRoot;
		} else {
		//key must be > localRoot, so recurse to the right
			localRoot.right = add(localRoot.right, key, p);
			//Make sure to fix tree!
			if(localRoot.right.priority > localRoot.priority){
				localRoot.rotateLeft();
			}
			return localRoot;
		}
	}
	
	boolean isDeleted = false;		//switches to true if deleted
	/**
	 * Deletes the node with the given key from the treap. 
	 * Returns true if successful, false if the key
	 * is not found
	 * 
	 * @param  key		Data of the node to be deleted
	 * @return boolean	true if successful, false otherwise	
	 */
	public boolean delete(E key){
		isDeleted = false;
		if(find(key) != null){	//Make sure key is in treap
			makeLeaf(key);		//first make the node to be deleted a leaf
			deleteLeaf(key);	//remove the leaf
		} 
		return isDeleted;
	}
	/**
	 * Makes the node to be deleted a leaf
	 * for easier deletion
	 * 
	 * @param key	Data of the node to be deleted
	 */
	public void makeLeaf(E key){
		Node<E> current = findNode(key);
		//Iterative rotation until node is leaf
		while(current.right != null || current.left != null){
			//System.out.println("makeLeaf while looping");
			//if left is null, rotate left
			if(current.left == null){			
				current.rotateLeft();
			//if right is null, rotate right
			}else if(current.right == null){	
				current.rotateRight();
			//if left priority > right priority, rotate right
			}else if(current.left.priority > current.right.priority){
				current.rotateRight();
			//else right priority > left priority, rotate left
			}else{
				current.rotateLeft();
			}
			current = findNode(key);	//update current
		}
	}

	/**
	 * Deletes the given key by rotating until
	 * the node containing the key is a leaf, and
	 * setting the pointer of the leaf node's 
	 * parent to null
	 * 
	 * @param key	Data of the node to be deleted
	 */
	private void deleteLeaf(E key){
		Node<E> parent = parent(key);
		Node<E> child = findNode(key);
		//if there is only a root in tree, delete.
		if(parent == root || child == root) {
			isDeleted = true;
			root = null;
			return;
		}
		//Delete it: check left and right
		//update isDeleted to true
		if(parent(key).left == child){
			parent.left = null;
			isDeleted = true;
		} else if(parent.right == child){
			parent.right = null;
			isDeleted = true;
		}
	}

	/**
	 * Finds the node with the given key
	 * Returns the key if found,
	 * or returns null if not found
	 * 
	 * @param  key	Data of the node to be found
	 * @return E	The data of the node found
	 */
	public E find(E key){
		//if the key is not null, call helper function
		return (key != null)? find(root, key): null;
	}

	/**
	 * Iteratively finds the node with the given key in the treap.
	 * Returns the key if found, returns null if not found.
	 * 
	 * @param  key	Data of the node to be found
	 * @return E	The data of the node found
	 */
	private E find(Node<E> root, E key){
		Node<E> current = root;
		//Iteratively compares current.data and key
		while(current != null){
			if(key.compareTo(current.data) > 0)			//if key > current.data,
				current = current.right;				//		move right
			else if (key.compareTo(current.data) < 0)	//if key < current.data,
				current = current.left;					//		move left
			else if(current != null)					//key must equal current.data
				return key;								//return found key
		}
		return null;	
	}
	
	/**
	 * Iteratively finds the node with the given key in the treap.
	 * Helper function for delete.
	 * Returns the node if found, or returns null if not found.
	 * 
	 * @param  key		Data of the node to be found
	 * @return Node<E>	The node found
	 */
	private Node<E> findNode(E key){
		Node<E> current = root;						
		while(current != null){						//same while loop as find function above
			if(key.compareTo(current.data) > 0)
				current = current.right;
			else if (key.compareTo(current.data) < 0)
				current = current.left;
			else if(current != null)
				return current;						//returns the Node instead of the key
		}
		return null;	
	}
	
	/**
	 * Returns parent of child, or null if (child == root).
	 * Helper function for delete.
	 * 
	 * @param 	key		The key of the child
	 * @return 	Node<E>	The child's parent
	 */
	private Node<E> parent(E key){
		Node<E> child = findNode(key);
		if(root == child)		//The root doesn't have a parent, return null
			return null;
		return parentHelper(child, root);
	}
	
	/**
	 * Recursively find the parent of a given node.
	 * 
	 * @param 	child	The child node
	 * @param 	parent	The parent node to be recursed
	 * @return 	Node<E> The parent of the child
	 */
	private Node<E> parentHelper(Node<E> child, Node<E> parent){
		// not found, potential parent is leaf
		if(parent.right == null && parent.left == null){
			return null;
		}
		//if left parent, return parent
		if(parent.left != null && parent.left.data.compareTo(child.data) == 0){ 
			return parent;
		}
		//if right parent, return parent
		if(parent.right != null && parent.right.data.compareTo(child.data) == 0){ 
			return parent;
		}
		//if data larger, recurse with parent.left
		if(parent.data.compareTo(child.data) > 0){
			return parentHelper(child, parent.left);
		}
		//else data smaller, recurse with parent.right
		return parentHelper(child, parent.right);
	}

	/**
	 * Carries out an inorder traversal of the tree to
	 * represent the nodes as a String. Each node with 
	 * key k, priority p, left child l, and right child
	 * r is represented as the string (l)[k,p](r)
	 * 
	 * @return 	String	Treap representation
	 */
	public String toString(){
		return toString(root, 1);
	}
	
	/**
	 * Recursive helper function for toString
	 * 
	 * @param 	localRoot	The current root node
	 * @param 	spacing		The correct spacing for each line
	 * @return	String		Treap representation
	 */
	private String toString(Node<E> localRoot, int spacing){
		StringBuilder output = new StringBuilder();		//The final output
		StringBuilder indenter = new StringBuilder();	//How much to indent
		
		for(int x = 0; x <= spacing; x++){				
			indenter.append(" ");		//append a space for every spacing added
		}
		if(localRoot != null){
			//the standard node in String form  "(key=?, priority=?)"
			output.append("(key=" + localRoot.data + ", priority=" + localRoot.priority + ")\n");
			//recursively append the left and right nodes. Adjust spacing on left.
			output.append(indenter + toString(localRoot.left,  spacing +=2));
			output.append(indenter + toString(localRoot.right, spacing ++));
		} else if(localRoot == null){
			//if node is null, append null
			output.append("null\n");
		}
		return output.toString();
	}

	
	public static void main(String[] args){
		/*		
		Treap<Integer> testTree = new Treap<Integer>();
		
		testTree.add(4,19);
		testTree.add(2,31);
		testTree.add(6,70);
		testTree.add(1,84);
		testTree.add(3,12);
		testTree.add(5,83);
		testTree.add(7,26);
		
		testTree.delete(6);
		
		System.out.println(testTree.toString());
 		*/
	}

}
