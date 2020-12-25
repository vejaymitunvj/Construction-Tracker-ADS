

import java.util.LinkedList;
import java.util.List;

public class RedBlackTreeRisingCity {

	final int blk = 1;
	final int rd = 0;

	public class RBNode {
		int clr = blk;

		risingCity nodeVal = null;

		RBNode left = null, right = null, pNode = null;

		RBNode(risingCity nodeVal) {

			this.nodeVal = nodeVal;

		}
	}

	RBNode grandParent(RBNode node) {

		return node.pNode.pNode;

	}
	
	 
	RBNode nullnode =null;
	
	
	public void calldelete(risingCity val) {
		int delNode=val.bNo;
		delete(delNode);
	}
	public void callInsert(risingCity val,risingCity nil1) {
		
		RBNode newNode = new RBNode(val);
		
		RBNode nil = new RBNode(nil1);
		nullnode = nil;
		newNode.left = nil;
		newNode.right = nil;
		newNode.pNode = nil;
		
		
		insert(newNode);
		
		
	}
	
	
	RBNode root = null;

	public void insert(RBNode newNode) {

		RBNode current = root;
		
		
//// ################ Setting root node ###########################
		if (root == null || root.nodeVal.bNo == nullnode.nodeVal.bNo) {
			root = newNode;
			root.pNode=nullnode;
			newNode.clr = blk;
			
			newNode.pNode = nullnode;
		}
		
		else {
			newNode.clr = rd;
		
			while (current.nodeVal.bNo != newNode.nodeVal.bNo && newNode.nodeVal.bNo != -1 && current.nodeVal.bNo != -1 ) {
////######################## New node has building number smaller than the existing node ###################################
				
				if (newNode.nodeVal.bNo < current.nodeVal.bNo) {
					if (current.left.nodeVal.bNo == -1) {

						current.left = newNode;
						newNode.pNode = current;
						break;

					} else {
						current = current.left;
					}
				}
				
////######################## New node has building number greater than the existing node ###################################

				else if (newNode.nodeVal.bNo > current.nodeVal.bNo) {
					if (current.right.nodeVal.bNo == -1) {

						current.right = newNode;
						newNode.pNode = current;
						break;

					} else {
						current = current.right;
					}
				}
				
////######################## New node having same building number as the  existing node ###################################
				
				else {
					System.out.println("Duplicate Building number");
					System.exit(0);
				}

			}

////######################  Stops program if same building number is entered  ###################################
			
			if (current.nodeVal.bNo == newNode.nodeVal.bNo) {
				System.out.println("Duplicate Building number");
				System.exit(0);
			}
			
		repairRBT(newNode);	
		
		}
		
		
	}
	
	String udirection = null;
	RBNode unode=nullnode;
////######################  retriving parent sibling nodes  ###################################	
	RBNode getUnodeValue(RBNode newNode) {
		if(newNode.pNode == grandParent(newNode).left ) {
			udirection = "right";
			return grandParent(newNode).right;
			
			
		}
		else {
			udirection = "left";
			return grandParent(newNode).left;
		}
		
		
	}
	
	
	
////######################  color swaps and  node rotation for insert  ###################################
	void repairRBT(RBNode newNode) {
		
		while(newNode!= root && newNode.pNode.clr == rd) {
			unode = getUnodeValue(newNode);
			   
			if (unode.nodeVal.bNo != -1 && unode.clr == rd) {
				
//	############ Performing clr change when parents sibling node are rd or uncle node is rd for RRr,LRr,RLr and LLr cases ################
				newNode.pNode.clr = 1;
					unode.clr = 1;
                   newNode.pNode.pNode.clr = 0;
                   newNode = grandParent(newNode);
                   
                   
               }
			else {
//############ Performing rotates and clr changes when pNode's sibling node is blk or uncle node is blk ################		 
			if (newNode.pNode.right == newNode) {
				  
//####################  RLb Case #####################
				newNode = newNode.pNode;
                 anitClockwiseRotate(newNode);
             } 
			else if (newNode.pNode.left == newNode) {
//####################  LRb Case #####################                
				newNode = newNode.pNode;
                clockwiseRotate(newNode);
            }
//############ clr change during RRb,LRb,RLb and LLb cases ##############
			newNode.pNode.clr = blk;
            grandParent(newNode).clr = rd;
			
            if(udirection.equals("right")) {
//####################  LLb Case #####################  
            	clockwiseRotate(grandParent(newNode));
            }
            else if(udirection.equals("left")) {
//####################  RRb Case #####################
            	anitClockwiseRotate(grandParent(newNode));
            }			
		}
		}
		root.clr=blk;
		
		
		
	}
	
////######################  rotating root node  ###################################
	void rotateRoot(String direction){
////######################  clockwise rotate root for LL imbalance  ###################################
		if(direction.equals("left")) {
			RBNode dummyRight = root.right;
            root.right = dummyRight.left;
            dummyRight.left.pNode = root;
            root.pNode = dummyRight;
            dummyRight.left = root;
            dummyRight.pNode = nullnode;
            root = dummyRight;
		}
		else if(direction.equals("right")) {
//######################  anti-clockwise rotate root for RR imbalance  ###################################			
        	RBNode dummyleft = root.left;
            root.left = root.left.right;
            dummyleft.right.pNode = root;
            root.pNode = dummyleft;
            dummyleft.right = root;
            dummyleft.pNode = nullnode;
            root = dummyleft;
			
		}
		
	}
	
////######################  anti-clockwise rotate root for LL imbalance and lr imbalance sub case ###################################
	void anitClockwiseRotate(RBNode newNode) {
        if (newNode.pNode.nodeVal.bNo != nullnode.nodeVal.bNo) {
            if (newNode == newNode.pNode.left) {
            	newNode.pNode.left = newNode.right;
            } else {
            	newNode.pNode.right = newNode.right;
            }
            newNode.right.pNode = newNode.pNode;
            newNode.pNode = newNode.right;
            if (newNode.right.left != null && newNode.right.left.nodeVal.bNo != nullnode.nodeVal.bNo) {
            	newNode.right.left.pNode = newNode;
            }
            newNode.right = newNode.right.left;
            newNode.pNode.left = newNode;
        } else {
        	rotateRoot("left");
        }
    }

////######################  clockwise rotate root for RR imbalance and RL sub case  ###################################
    void clockwiseRotate(RBNode newNode) {
        if (newNode.pNode.nodeVal.bNo != nullnode.nodeVal.bNo) {
            if (newNode == newNode.pNode.left) {
            	newNode.pNode.left = newNode.left;
            } else {
            	newNode.pNode.right = newNode.left;
            }

            newNode.left.pNode = newNode.pNode;
            newNode.pNode = newNode.left;
            if ( newNode.left.right != null && newNode.left.right.nodeVal.bNo != nullnode.nodeVal.bNo) {
            	newNode.left.right.pNode = newNode;
            }
            newNode.left = newNode.left.right;
            newNode.pNode.right = newNode;
        } else {
        	rotateRoot("right");
        }
    }

////######################  To search for node from the tree  ###################################    
    RBNode searchRBNode(int findtarget, RBNode treeNode) {
        if (root == null) {
            return root;
        }

        if (findtarget < treeNode.nodeVal.bNo) {
            if (treeNode.left != null && treeNode.left.nodeVal.bNo != nullnode.nodeVal.bNo ) {
                return searchRBNode(findtarget, treeNode.left);
            }
        } else if (findtarget > treeNode.nodeVal.bNo) {
            if (treeNode.right != null && treeNode.right.nodeVal.bNo != nullnode.nodeVal.bNo ) {
                return searchRBNode(findtarget, treeNode.right);
            }
        } else if (findtarget == treeNode.nodeVal.bNo) {
            return treeNode;
        }
        return null;
    }
    
////######################  finding the lowest node from right subtree for delete for BST delete  ###################################
    RBNode deleteReplacement(RBNode subNode){
        while(subNode.left.nodeVal.bNo != nullnode.nodeVal.bNo){
        	subNode = subNode.left;
        }
        return subNode;
    }
   
////######################  replacing the deleted node with respective child nodes  ###################################    
    void replace(RBNode targetNode, RBNode rNode){ 
        if(targetNode.pNode.nodeVal.bNo == nullnode.nodeVal.bNo){
            root = rNode;
        }else if(targetNode == targetNode.pNode.left){
        	targetNode.pNode.left = rNode;
        }else
        	targetNode.pNode.right = rNode;
        rNode.pNode = targetNode.pNode;
  }
    
////######################  function to delete the given building number  ###################################    
    void delete(int bldno){
        RBNode delNode; 
        delNode= searchRBNode(bldno, root);
        if(delNode!=null) {
    	RBNode subNode;
    	RBNode tempDel = delNode;
    	
        int oClr = tempDel.clr;
        
        if(delNode.left.nodeVal.bNo == nullnode.nodeVal.bNo){
        	subNode = delNode.right;  
            replace(delNode, delNode.right);  
        }else if(delNode.right.nodeVal.bNo == nullnode.nodeVal.bNo){
        	subNode = delNode.left;
            replace(delNode, delNode.left); 
        }else{
        	tempDel = deleteReplacement(delNode.right);
            oClr = tempDel.clr;
            subNode = tempDel.right;
            if(tempDel.pNode == delNode)
            	subNode.pNode = tempDel;
            else{
            	replace(tempDel, tempDel.right);
            	tempDel.right = delNode.right;
            	tempDel.right.pNode = tempDel;
            }
            replace(delNode, tempDel);
            tempDel.left = delNode.left;
            tempDel.left.pNode = tempDel;
            tempDel.clr = delNode.clr; 
        }
        if(oClr == 1)
            deleteFixup(subNode);  
    }
    }

////######################  handling delete fixes  ###################################   
    void deleteFixup(RBNode nde){
////###################### fix from deleted node uptill root ###################################
    	while(nde!=root && nde.clr == blk){ 
////###################### color switch ###################################
    		if(nde == nde.pNode.left){
                RBNode hlpDelNode = nde.pNode.right;
////###################### color switch for child node red ###################################
                if(hlpDelNode.clr == rd){
                	hlpDelNode.clr = blk;
                    nde.pNode.clr = rd;
                    anitClockwiseRotate(nde.pNode);
                    hlpDelNode = nde.pNode.right;
                }
////###################### color switch ###################################                
                if(hlpDelNode.left.clr == blk && hlpDelNode.right.clr == blk){
                	hlpDelNode.clr = rd;
                    nde = nde.pNode;
                    continue;
                }
////###################### color switch ###################################
                else if(hlpDelNode.right.clr == blk){
                	hlpDelNode.left.clr = blk;
                	hlpDelNode.clr = rd;
                    clockwiseRotate(hlpDelNode);
                    hlpDelNode = nde.pNode.right;
                }
                if(hlpDelNode.right.clr == rd){
                	hlpDelNode.clr = nde.pNode.clr;
                    nde.pNode.clr = blk;
                    hlpDelNode.right.clr = blk;
                    anitClockwiseRotate(nde.pNode);
                    nde = root;
                }
            }else{
////###################### color switch ###################################            	
                RBNode hlpDelNode = nde.pNode.left;
                if(hlpDelNode.clr == rd){
                	hlpDelNode.clr = blk;
                    nde.pNode.clr = rd;
                    clockwiseRotate(nde.pNode);
                    hlpDelNode = nde.pNode.left;
                }
////###################### color switch ###################################                
                if(hlpDelNode.right.clr == blk && hlpDelNode.left.clr == blk){
                	hlpDelNode.clr = rd;
                    nde = nde.pNode;
                    continue;
                }
////######################  re-color and RR imbalance rotate   ###################################                
                else if(hlpDelNode.left.clr == blk){
                	hlpDelNode.right.clr = blk;
                	hlpDelNode.clr = rd;
                    anitClockwiseRotate(hlpDelNode);
                    hlpDelNode = nde.pNode.left;
                }
////######################  re-color and LL imbalance rotate   ###################################
                if(hlpDelNode.left.clr == rd){
                	hlpDelNode.clr = nde.pNode.clr;
                    nde.pNode.clr = blk;
                    hlpDelNode.left.clr = blk;
                    clockwiseRotate(nde.pNode);
                    nde = root;
                }
            }
        }
        nde.clr = 1; 
    }
    
    RBNode singlePrint(int buildingNo) {
    	
    	RBNode rb = searchRBNode(buildingNo, root);
    	return rb;

    	}
    
    
    
    

    
   
    List<risingCity> searchInRange(int key1, int key2) {
    	List<risingCity> printRangeList= new LinkedList<risingCity>();
    	 
        searchInRange(root, printRangeList, key1, key2);
        return printRangeList;
        
    }

   
    private void searchInRange(RBNode snode, List<risingCity> printRangeList, int start, int end) {
        RBNode snode2 = snode;
		if (snode2 == null && snode2.nodeVal.bNo == nullnode.nodeVal.bNo ) {
            return;
        }
        
        
        if(snode2.nodeVal.bNo != -1 && snode2 != null) {
        if (start < snode2.nodeVal.bNo) {
            searchInRange(snode2.left, printRangeList, start, end);
        }

        if (start <= snode2.nodeVal.bNo && end >= snode2.nodeVal.bNo) {
            printRangeList.add(snode2.nodeVal);
        }

        if (end > snode2.nodeVal.bNo) {
            searchInRange(snode2.right, printRangeList, start, end);
        }
        }
       
    }
    
//// ################################## end of class ######################################	
}



