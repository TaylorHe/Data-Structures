import java.util.List;

/**
 * A class to represent a binary search tree.
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
	// Data Fields
	/** Return value from the public add method. */
	protected boolean addReturn;
	/** Return value from the public delete method. */
	protected E deleteReturn;

	// Methods
	/**
	 * Starter method find.
	 * 
	 * @pre The target object must implement the Comparable interface.
	 * @param target
	 *            The Comparable object being sought
	 * @return The object, if found, otherwise null
	 */
	public E find(E target) {
		return find(root, target);
	}

	/**
	 * Recursive find method.
	 * 
	 * @param localRoot
	 *            The local subtree�s root
	 * @param target
	 *            The object being sought
	 * @return The object, if found, otherwise null
	 */
	private E find(Node<E> localRoot, E target) {
		if (localRoot == null) {
			return null;
		}

		// Compare the target with the data field at the root.
		int compResult = target.compareTo(localRoot.data);
		if (compResult == 0) {
			return localRoot.data;
		} else if (compResult < 0) {
			return find(localRoot.left, target);
		} else {
			return find(localRoot.right, target);
		}
	}

	/**
	 * Starter method add.
	 * 
	 * @pre The object to insert must implement the Comparable interface.
	 * @param item
	 *            The object being inserted
	 * @return true if the object is inserted, false if the object already
	 *         exists in the tree
	 */
	public boolean add(E item) {
		root = add(root, item);
		return addReturn;
	}

	/**
	 * Recursive add method.
	 * 
	 * @post The data field addReturn is set true if the item is added to the
	 *       tree, false if the item is already in the tree.
	 * @param localRoot
	 *            The local root of the subtree
	 * @param item
	 *            The object to be inserted
	 * @return The new local root that now contains the inserted item
	 */
	private Node<E> add(Node<E> localRoot, E item) {
		if (localRoot == null) {
			// item is not in the tree � insert it.
			addReturn = true;
			return new Node<E>(item);
		} else if (item.compareTo(localRoot.data) == 0) {
			// item is equal to localRoot.data
			addReturn = false;
			return localRoot;
		} else if (item.compareTo(localRoot.data) < 0) {
			// item is less than localRoot.data
			localRoot.left = add(localRoot.left, item);
			return localRoot;
		} else {
			// item is greater than localRoot.data
			localRoot.right = add(localRoot.right, item);
			return localRoot;
		}
	}

	/**
	 * Starter method delete.
	 * 
	 * @post The object is not in the tree.
	 * @param target
	 *            The object to be deleted
	 * @return The object deleted from the tree or null if the object was not in
	 *         the tree
	 * @throws ClassCastException
	 *             if target does not implement Comparable
	 */
	public E delete(E target) {
		root = delete(root, target);
		return deleteReturn;
	}

	/**
	 * Recursive delete method.
	 * 
	 * @post The item is not in the tree; deleteReturn is equal to the deleted
	 *       item as it was stored in the tree or null if the item was not
	 *       found.
	 * @param localRoot
	 *            The root of the current subtree
	 * @param item
	 *            The item to be deleted
	 * @return The modified local root that does not contain the item
	 */
	private Node<E> delete(Node<E> localRoot, E item) {
		if (localRoot == null) {
			// item is not in the tree.
			deleteReturn = null;
			return localRoot;
		}

		// Search for item to delete.
		int compResult = item.compareTo(localRoot.data);
		if (compResult < 0) {
			// item is smaller than localRoot.data.
			localRoot.left = delete(localRoot.left, item);
			return localRoot;
		} else if (compResult > 0) {
			// item is larger than localRoot.data.
			localRoot.right = delete(localRoot.right, item);
			return localRoot;
		} else {
			// item is at local root.
			deleteReturn = localRoot.data;
			if (localRoot.left == null) {
				// If there is no left child, return right child
				// which can also be null.
				return localRoot.right;
			} else if (localRoot.right == null) {
				// If there is no right child, return left child.
				return localRoot.left;
			} else {
				// Node being deleted has 2 children, replace the data
				// with inorder predecessor.
				if (localRoot.left.right == null) {
					// The left child has no right child.
					// Replace the data with the data in the
					// left child.
					localRoot.data = localRoot.left.data;
					// Replace the left child with its left child.
					localRoot.left = localRoot.left.left;
					return localRoot;
				} else {
					// Search for the inorder predecessor (ip) and
					// replace deleted node's data with ip.
					localRoot.data = findLargestChild(localRoot.left);
					return localRoot;
				}
			}
		}
	}

	/**
	 * Find the node that is the inorder predecessor and replace it with its
	 * left child (if any).
	 * 
	 * @post The inorder predecessor is removed from the tree.
	 * @param parent
	 *            The parent of possible inorder predecessor (ip)
	 * @return The data in the ip
	 */
	private E findLargestChild(Node<E> parent) {
		// If the right child has no right child, it is
		// the inorder predecessor.
		if (parent.right.right == null) {
			E returnValue = parent.right.data;
			parent.right = parent.right.left;
			return returnValue;
		} else {
			return findLargestChild(parent.right);
		}
	}


	/**
	 * Compute the distance from the root of the target. If the target is the
	 * root, then the distance is zero.
	 * 
	 * @param target
	 *            the key for which the distance from the root is to be
	 *            computed.
	 * @return the distance of the target from the root. Returns a negative
	 *         integer if the target key was not found.
	 */
	public int getDistance(E target){
		return getDistance(target, this);
	}
	public int getDistance(E target, BinaryTree<E> currentTree) {
		if(currentTree.root == null){
			return -1;
		} else if(currentTree.root == target){
			return 0;
		}else{
			return 1 + Math.min(getDistance(target, currentTree.getLeftSubtree()), 
					getDistance(target, currentTree.getRightSubtree()));
		}
	}

	/**
	 * Returns the list containing the keys of a longest path from the root up
	 * to a leaf. If there are multiple longest paths, only one longest path is
	 * considered.
	 * 
	 * @return a list containing the keys of a longest path from the root to a leaf.
	 */
	public List<E> findLongestPath() {
		// TODO
		return null;
	}

	/**
	 * Deletes all leafs of this tree.
	 */
	public void trim(){
		trim(this);
	}
	public void trim(BinaryTree<E> tree) {
		if(tree.root.right == null && tree.root.left == null){
			this.delete(tree.root, root.data);
		}
		if(tree.getLeftSubtree()!=null){
		trim(this.getLeftSubtree());}
		if(tree.getLeftSubtree()!=null){
		trim(this.getRightSubtree());}

	}

	/**
	 * Tests.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		tree.add(24);
		tree.add(18);
		tree.add(67);
		tree.add(68);
		tree.add(69);
		tree.add(25);tree.add(19);tree.add(20);tree.add(11);tree.add(93);
		System.out.println(tree.toString());
	}
}
