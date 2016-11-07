//Taylor He
//I pledge my honor that I have abided by the Stevens Honor System.

package homework4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TreapTest {
	Treap<Integer> testTree = new Treap<Integer>(); 
	
	@Test
	public final void testAdd() {
		assert(testTree.add(4,19) == true); 
		assert(testTree.add(2,31) == true); 
		assert(testTree.add(6,70) == true); 
		assert(testTree.add(1,84) == true); 
		assert(testTree.add(3,12) == true); 
		assert(testTree.add(5,83) == true); 
		assert(testTree.add(7,26) == true);
	}
	@Test
	public final void testDelete() {
		testTree.add(4,19);
		testTree.add(2,31);
		testTree.add(6,70);
		testTree.add(1,84);
		testTree.add(3,12);
		testTree.add(5,83);
		testTree.add(7,26);
		
		assertTrue(testTree.delete(7));
		assertTrue(testTree.delete(1));
		assertFalse(testTree.delete(10));
	}
	@Test
	public final void testFind(){
		testTree.add(4,19);
		testTree.add(2,31);
		testTree.add(6,70);
		testTree.add(1,84);
		testTree.add(3,12);
		testTree.add(5,83);
		testTree.add(7,26);
		
		assertTrue(testTree.find(1) == 1);
		assertTrue(testTree.find(3) == 3);
		assertTrue(testTree.find(4) == 4);
		assertTrue(testTree.find(8) == null);
	}
	@Test
	public final void testToString() {
		testTree.add(4,19);
		assertTrue(testTree.toString().equals("(key=4, priority=19)\n  null\n  null\n"));
		assertTrue(testTree.delete(4));
		assertTrue(testTree.toString().equals("null\n"));
	}
	

}
