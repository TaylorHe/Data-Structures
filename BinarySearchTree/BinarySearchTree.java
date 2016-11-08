package BinaryTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class for a binary tree that stores type E objects.
 **/
public class BinaryTree<E> {
	/** Class to encapsulate a tree node. */
	protected static class Node<E> {
		// Data Fields
		/** The information stored in this node. */
		public E data;
		/** Reference to the left child. */
		public Node<E> left;
		/** Reference to the right child. */
		public Node<E> right;

		// Constructors
		/**
		 * Construct a node with given data and no children.
		 * 
		 * @param data
		 *            The data to store in this node
		 */
		public Node(E data) {
			this.data = data;
			left = null;
			right = null;
		}

		// Methods
		/**
		 * Returns a string representation of the node.
		 * 
		 * @return A string representation of the data fields
		 */
		@Override
		public String toString() {
			return data.toString();
		}
	}
	// Data Field
	/** The root of the binary tree */
	protected Node<E> root;

	/** Construct an empty BinaryTree */
	public BinaryTree() {
		root = null;
	}

	/**
	 * Construct a BinaryTree with a specified root. Should only be used by
	 * subclasses.
	 * 
	 * @param root
	 *            The node that is the root of the tree.
	 */
	protected BinaryTree(Node<E> root) {
		this.root = root;
	}

	public BinaryTree(E data) {
		this(data, null, null);
	}

	/**
	 * Constructs a new binary tree with data in its root,leftTree as its left
	 * subtree and rightTree as its right subtree.
	 */
	public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		root = new Node<E>(data);
		if (leftTree != null) {
			root.left = leftTree.root;
		} else {
			root.left = null;
		}
		if (rightTree != null) {
			root.right = rightTree.root;
		} else {
			root.right = null;
		}
	}

	/**
	 * Return the left subtree.
	 * 
	 * @return The left subtree or null if either the root or the left subtree
	 *         is null
	 */
	public BinaryTree<E> getLeftSubtree() {
		if (root != null && root.left != null) {
			return new BinaryTree<E>(root.left);
		} else {
			return null;
		}
	}

	/**
	 * Return the right sub-tree
	 * 
	 * @return the right sub-tree or null if either the root or the right
	 *         subtree is null.
	 */
	public BinaryTree<E> getRightSubtree() {
		if (root != null && root.right != null) {
			return new BinaryTree<E>(root.right);
		} else {
			return null;
		}
	}

	/**
	 * Return the data field of the root
	 * 
	 * @return the data field of the root or null if the root is null
	 */
	public E getData() {
		if (root != null) {
			return root.data;
		} else {
			return null;
		}
	}

	/**
	 * Determine whether this tree is a leaf.
	 * 
	 * @return true if the root has no children
	 */
	public boolean isLeaf() {
		return (root == null || (root.left == null && root.right == null));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		preOrderTraverse(root, 1, sb);
		return sb.toString();
	}

	/**
	 * Perform a preorder traversal.
	 * 
	 * @param node
	 *            The local root
	 * @param depth
	 *            The depth
	 * @param sb
	 *            The string buffer to save the output
	 */
	private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
		for (int i = 1; i < depth; i++) {
			sb.append("  ");
		}
		if (node == null) {
			sb.append("null\n");
		} else {
			sb.append(node.toString());
			sb.append("\n");
			preOrderTraverse(node.left, depth + 1, sb);
			preOrderTraverse(node.right, depth + 1, sb);
		}
	}

	/**
	 * Method to read a binary tree.
	 * 
	 * @pre The input consists of a preorder traversal of the binary tree. The
	 *      line "null" indicates a null tree.
	 * @param bR
	 *            The input file
	 * @return The binary tree
	 * @throws IOException
	 *             If there is an input error
	 */
	public static BinaryTree<String> readBinaryTree(BufferedReader bR) throws IOException {
		// Read a line and trim leading and trailing spaces.
		if (bR == null) {
			throw new IllegalArgumentException();
		}

		String data = bR.readLine();
		if ((data == null) || data.equals("null")) {
			return null;
		} else {
			data = data.trim();
			BinaryTree<String> leftTree = readBinaryTree(bR);
			BinaryTree<String> rightTree = readBinaryTree(bR);
			return new BinaryTree<String>(data, leftTree, rightTree);
		}
	}

	/**
	 * Method to return the preorder traversal of the binary tree as a sequence
	 * of strings each separated by a space.
	 * 
	 * @return A preorder traversal as a string
	 */
	public String preorderToString() {
		StringBuilder stb = new StringBuilder();
		preorderToString(stb, root);
		return stb.toString();
	}

	private void preorderToString(StringBuilder stb, Node<E> root) {
		stb.append(root);
		if (root.left != null) {
			stb.append(" ");
			preorderToString(stb, root.left);
		}
		if (root.right != null) {
			stb.append(" ");
			preorderToString(stb, root.right);
		}
	}

	/**
	 * Method to return the postorder traversal of the binary tree as a sequence
	 * of strings each separated by a space.
	 * 
	 * @return A postorder traversal as a string
	 */
	public String postorderToString() {
		StringBuilder stb = new StringBuilder();
		postorderToString(stb, root);
		return stb.toString();
	}

	private void postorderToString(StringBuilder stb, Node<E> root) {
		if (root.left != null) {
			postorderToString(stb, root.left);
			stb.append(" ");
		}
		if (root.right != null) {
			postorderToString(stb, root.right);
			stb.append(" ");
		}
		stb.append(root);
	}

	/**
	 * A method to display the inorder traversal of a binary tree placeing a
	 * left parenthesis before each subtree and a right parenthesis after each
	 * subtree. For example the expression tree shown in Figure 6.12 would be
	 * represented as (((x) + (y)) * ((a) / (b))).
	 * 
	 * @return An inorder string representation of the tree
	 */
	public String inorderToString() {
		StringBuilder stb = new StringBuilder();
		inorderToString(stb, root);
		return stb.toString();
	}

	private void inorderToString(StringBuilder stb, Node<E> root) {
		if (root != null) {
			if (root.left != null) {
				stb.append("(");
				inorderToString(stb, root.left);
				stb.append(") ");
			}
			stb.append(root);
			if (root.right != null) {
				stb.append(" (");
				inorderToString(stb, root.right);
				stb.append(")");
			}
		}
	}


	public int size() {
		if (root == null) {
			return 0;
		} else {
			int left = root.left == null ? 0 : getLeftSubtree().size();
			int right = root.right == null ? 0 : getRightSubtree().size();
			return 1 + left + right;
		}
	}

	public int height() {
		if (root == null) {
			return 0;
		} else {
			int left = root.left == null ? 0 : getLeftSubtree().height();
			int right = root.right == null ? 0 : getRightSubtree().height();
			return 1 + Math.max(left, right);
		}
	}

	public boolean isFull() {
		if (isLeaf()) {
			return true;
		} else {
			return root.left != null && root.right != null && getLeftSubtree().isFull() && getRightSubtree().isFull();
		}
	}

	public boolean isComplete() {
		boolean result = true;
		Queue<Node<E>> q = new LinkedList<>();
		Node<E> current = root;

		// Go through nodes level by level and from left to right
		// until we find a node that is null
		while (current != null) {
			q.offer(current.left);
			q.offer(current.right);
			current = q.poll();
		}

		// If the tree is complete, all elements left in the queue must be null
		for (Node<E> node : q) {
			if (node != null) {
				result = false;
				break;
			}
		}

		return result;
	}

	public boolean isPerfect() {
		return (Math.pow(2, height()) - 1) == size();
	}

	public static void main(String[] args) throws IOException {
		// keep Fig_6_12.txt in your home folder or change the path below
		String treeString = System.getProperty("user.home") + File.separator + "Fig_6_12.txt";
		BufferedReader br = new BufferedReader(new FileReader(treeString));
		BinaryTree<String> tree = BinaryTree.readBinaryTree(br);
		BinaryTree<String> bt0 = new BinaryTree<>();
		BinaryTree<String> bt1 = new BinaryTree<>("x");
		BinaryTree<String> bt2 = new BinaryTree<>("x", new BinaryTree<>("l"), new BinaryTree<>("r"));
		BinaryTree<String> bt3 = new BinaryTree<>("x", new BinaryTree<>("l"),
				new BinaryTree<>("r", new BinaryTree<>("rl"), new BinaryTree<>("rr")));
		BinaryTree<String> bt4 = new BinaryTree<>("x", new BinaryTree<>("l"), null);
		BinaryTree<String> bt5 = new BinaryTree<>("x",
				new BinaryTree<>("l", new BinaryTree<>("ll"), new BinaryTree<>("lr")),
				new BinaryTree<>("r", new BinaryTree<>("rl"), new BinaryTree<>("rr")));
		BinaryTree<String> bt6 = new BinaryTree<>("x",
				new BinaryTree<>("l", new BinaryTree<>("ll"), new BinaryTree<>("lr")),
				new BinaryTree<>("r", new BinaryTree<>("rl"), null));
		BinaryTree<String> bt7 = new BinaryTree<>("x",
				new BinaryTree<>("l", new BinaryTree<>("ll"), new BinaryTree<>("lr")), new BinaryTree<>("r"));
		BinaryTree<String> bt8 = new BinaryTree<>("x",
				new BinaryTree<>("l", new BinaryTree<>("ll"), new BinaryTree<>("lr")),
				new BinaryTree<>("r", null, new BinaryTree<>("rr")));
		BinaryTree<String> bt9 = new BinaryTree<>("x", new BinaryTree<>("l", new BinaryTree<>("ll"), null),
				new BinaryTree<>("r", new BinaryTree<>("rl"), new BinaryTree<>("rr")));
		List<BinaryTree<String>> bts = Arrays.asList(tree, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9);

		for (BinaryTree<String> bt : bts) {
			System.out.println(bt.inorderToString());
			System.out.println("\tsize: " + bt.size());
			System.out.println("\theight: " + bt.height());
			System.out.println("\tis full:: " + bt.isFull());
			System.out.println("\tis complete:: " + bt.isComplete());
			System.out.println("\tis perfect:: " + bt.isPerfect());
		}
	}
}
/* </listing> */
