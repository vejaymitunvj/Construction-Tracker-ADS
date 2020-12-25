

import java.io.IOException;

public class MinHeapRisingCity {

	risingCity[] riseHeap;
	int size;
	private int riseMaxNumOfBuilding;
	static final int root = 1;

	public MinHeapRisingCity(int riseMaxNumOfBuilding) {
		this.riseMaxNumOfBuilding = riseMaxNumOfBuilding;
		riseHeap = new risingCity[this.riseMaxNumOfBuilding + 1];

//	####	inserting item to fill 0th with min value position ####

		risingCity dummyItem = new risingCity();
		dummyItem.bNo = Integer.MIN_VALUE;
		dummyItem.eTOB = Integer.MIN_VALUE;
		dummyItem.tTOB = Integer.MIN_VALUE;

		riseHeap[0] = dummyItem;

	}

	// ##################### retruning parent index #######################
	int parent(int index) {
		return index / 2;

	}

	// ##################### retruning left child index #######################
	int leftChild(int index) {
		return (2 * index);

	}
	//##################### retruning right child index #######################
	int rightChild(int index) {
		return ((2 * index) + 1);

	}

	//##################### finding leaf node #######################
	boolean isLeafNode(int index) {

		if (index >= ((size / 2) + 1) && index <= size) {
			return true;
		}
		return false;
	}

	//##################### node Swap  #######################
	void swapNodes(int i, int j) {

		risingCity tmp;
		tmp = riseHeap[i];
		riseHeap[i] = riseHeap[j];
		riseHeap[j] = tmp;
	}

	//##################### Minheapify with respect to execution time #################### 
	//### when multiple building has same execution time ties are broken building numbers###
	void minHeapify(int index) {
		if (size > 0) {
			if (!isLeafNode(index)) {
//			################## left and right child are not null ####################
				if (riseHeap[rightChild(index)] != null && riseHeap[leftChild(index)] != null) {

					if (riseHeap[index].eTOB > riseHeap[leftChild(index)].eTOB
							|| riseHeap[index].eTOB > riseHeap[rightChild(index)].eTOB) {

						if (riseHeap[leftChild(index)].eTOB < riseHeap[rightChild(index)].eTOB) {
							swapNodes(index, leftChild(index));
							minHeapify(leftChild(index));
						}
//					################## left and right child are have equal execution time ################## 
//					 ################## comparing their building number to break ties ####################
						else if (riseHeap[leftChild(index)].eTOB == riseHeap[rightChild(index)].eTOB) {
							if (riseHeap[leftChild(index)].bNo < riseHeap[rightChild(index)].bNo) {
								swapNodes(index, leftChild(index));
								minHeapify(leftChild(index));
							} else {
								swapNodes(index, rightChild(index));
								minHeapify(rightChild(index));
							}
						} else {
							swapNodes(index, rightChild(index));
							minHeapify(rightChild(index));
						}
					} else if (riseHeap[index].eTOB == riseHeap[leftChild(index)].eTOB
							&& riseHeap[index].eTOB == riseHeap[rightChild(index)].eTOB) {

						if (riseHeap[index].bNo < riseHeap[leftChild(index)].bNo) {

							if (riseHeap[index].bNo > riseHeap[rightChild(index)].bNo) {

								swapNodes(index, rightChild(index));
								minHeapify(rightChild(index));

							}

						} else {
							if (riseHeap[leftChild(index)].bNo > riseHeap[rightChild(index)].bNo) {

								swapNodes(index, rightChild(index));
								minHeapify(rightChild(index));

							} else {
								swapNodes(index, leftChild(index));
								minHeapify(leftChild(index));
							}

						}

					} else {
						if (riseHeap[index].eTOB == riseHeap[leftChild(index)].eTOB) {
							if (riseHeap[index].bNo > riseHeap[leftChild(index)].bNo) {

								swapNodes(index, leftChild(index));
								minHeapify(leftChild(index));

							}
						} else if (riseHeap[index].eTOB == riseHeap[rightChild(index)].eTOB) {
							if (riseHeap[index].bNo > riseHeap[rightChild(index)].bNo) {
							swapNodes(index, rightChild(index));
							minHeapify(rightChild(index));
							}
						}

					}

				}
//##################### right child is null #######################
				else if (riseHeap[rightChild(index)] == null) {
					if (riseHeap[index].eTOB > riseHeap[leftChild(index)].eTOB) {
						swapNodes(index, leftChild(index));
						minHeapify(leftChild(index));
					} else if (riseHeap[index].eTOB == riseHeap[leftChild(index)].eTOB) {
						if (riseHeap[index].bNo > riseHeap[leftChild(index)].bNo) {
							swapNodes(index, leftChild(index));
							minHeapify(leftChild(index));
						}
					}

				}
			}
		}
	}

	//##################### insertion of building into the heap #######################
	public void construct(risingCity building)

	{
		
		if (size >= riseMaxNumOfBuilding) {
			return;
		}
		riseHeap[++size] = building;

		int newnode = size;
		//##################### moving the building up with lower execution time ###################
		//#####################and during ties broken using building numbers #######################
		while (parent(newnode) != root && newnode > root) {

			if (riseHeap[newnode].eTOB < riseHeap[parent(newnode)].eTOB) {
				swapNodes(newnode, parent(newnode));
				newnode = parent(newnode);
			} else if (riseHeap[newnode].eTOB == riseHeap[parent(newnode)].eTOB) {
				if (riseHeap[newnode].bNo < riseHeap[parent(newnode)].bNo) {
					swapNodes(newnode, parent(newnode));
					newnode = parent(newnode);

				} else {
					newnode = parent(newnode);
				}

			}

		}
		

	}
	 
	//##################### Removing buildings from the heap when the execution of building is done ###################
	risingCity removeMinRisingCiti() {

		risingCity removeFinishedBuildings = riseHeap[root];

		riseHeap[root] = riseHeap[size];
		riseHeap[size] = null;
		size--;
		minHeapify(root);

		return removeFinishedBuildings;
	}

}
