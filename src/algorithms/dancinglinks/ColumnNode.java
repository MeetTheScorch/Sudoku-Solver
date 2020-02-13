package algorithms.dancinglinks;

public class ColumnNode extends DancingNode{

	int size;
	String name;
	
	ColumnNode(String name) {
		super();
		size = 0;
		this.name = name;
		C = this;
	}
	
	void cover() {
		unlinkLR();
		for(DancingNode i = this.D; i != this; i = i.D) {
			for(DancingNode j = i.R; j != i; j = j.R) {
				j.unlinkUD();
				j.C.size--;
			}
		}
	}
	
	void uncover() {
		for(DancingNode i = this.U; i != this; i = i.U) {
			for(DancingNode j = i.L; j != i; j = j.L) {
				j.C.size++;
				j.relinkUD();
			}
		}
		relinkLR();
	}
}
