package com.shanyangcode.algoHW;

public class LinkedListProblems {

    //节点类（构造器），单链表初始化方法，单链表打印方法

    //节点
    class LinkNode {
        int value;
        LinkNode next;

        LinkNode(int value) {
            this.value = value;
        }
    }

    //初始化单链表
    public LinkNode creatLinkNode(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        LinkNode head = new LinkNode(arr[0]);
        LinkNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            LinkNode node = new LinkNode(arr[i]);
            cur.next = node;
            cur = cur.next;
        }
        return head;
    }

    //打印单链表
    public void printLinkNode(LinkNode head) {
        StringBuilder sb = new StringBuilder();
        LinkNode cur = head;
        while (cur != null) {
            sb.append(cur.value);
            if (cur.next != null) sb.append("->");
            cur = cur.next;
        }
        sb.append("->null");
        System.out.println(sb.toString());
    }


    //反转链表+定位中点+合并链表

    //反转链表
    // 1->3->5->6->null    6->5->3->1->null 
    public LinkNode reverseList(LinkNode head) {
        LinkNode pre = null;
        LinkNode cur = head;
        while (cur != null) {
            LinkNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    //定位中点
    //快慢指针
    public LinkNode middleNode1(LinkNode head) {
        LinkNode fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //思考：如果改成while(fast!=null&&fast.next!=null)
    //若链表内元素为奇数个结果不变，若为偶数个则为第size/2+1个
    //数学
    public LinkNode middleNode2(LinkNode head) {
        int size = countSize(head);
        int count = (size - 1) / 2;
        LinkNode cur = head;
        for (int i = 0; i < count; i++) {
            cur = cur.next;
        }
        return cur;
    }

    //辅助方法
    private int countSize(LinkNode head) {
        int count = 0;
        LinkNode cur = head;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
    }

    //合并链表：哨兵节点的使用，简化头节点判断
    public LinkNode mergeTwoLists(LinkNode head1, LinkNode head2) {
        LinkNode dommynode = new LinkNode(0);
        LinkNode cur = dommynode;
        LinkNode h1 = head1, h2 = head2;
        while (h1 != null && h2 != null) {
            if (h1.value <= h2.value) {
                cur.next = h1;
                h1 = h1.next;
            } else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }
        if (h1 != null) {
            cur.next = h1;
        } else {
            cur.next = h2;
        }
        return dommynode.next;
    }


    //环问题：判环，环入口，环长度

    //判环
    public boolean hasCycle(LinkNode head) {
        if (head == null) {
            return false;
        }
        LinkNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    //环入口
    public LinkNode cycleEntrance(LinkNode head) {
        if (head == null) {
            return null;
        }
        LinkNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                LinkNode cur = head;
                while (cur != slow) {
                    cur = cur.next;
                    slow = slow.next;
                }
                return cur;
            }
        }
        return null;
    }

    //环长度
    public int cycleLength(LinkNode head) {
        if (head == null) return 0;
        if (!hasCycle(head)) return 0;
        LinkNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                LinkNode cur = slow.next;
                int count = 1;
                while (cur != slow) {
                    cur = cur.next;
                    count++;
                }
                return count;
            }
        }
        return 0;
    }


    //重排链表
    //快慢指针找中点，断开，反转后面的，插入
    public void reorderLink(LinkNode head) {
        //快慢指针找中点，可添加辅助方法
        LinkNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //反转链表，可添加辅助方法
        LinkNode cur = slow.next;
        LinkNode pre = null;
        while (cur != null) {
            LinkNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        slow.next = null;
        //插入
        LinkNode firstHalf = head;
        LinkNode secondHalf = pre;
        LinkNode dommynode = new LinkNode(0);
        LinkNode currentnode = dommynode;
        while (secondHalf != null) {
            currentnode.next = firstHalf;
            firstHalf = firstHalf.next;
            currentnode = currentnode.next;

            currentnode.next = secondHalf;
            secondHalf = secondHalf.next;
            currentnode = currentnode.next;
        }
        currentnode.next = firstHalf;
    }

    //链表升序
    //一个链表，单索引是递增的，双索引是递减的，请对它进行升序排序，要求 0 （ 1 ）空间复杂度
    public void reorderList1(LinkNode head) {
        //分开
        LinkNode h1 = head, h2 = head.next;
        LinkNode firstHalf = head, secondHalf = head.next;
        while (h1 != null && h2 != null) {
            h1.next = h2.next;
            h1 = h1.next;
            h2.next = h1.next;
            h2 = h2.next;
        }
//        printLinkNode(firstHalf);
//        printLinkNode(secondHalf);

        //反转
        LinkNode cur = secondHalf;
        LinkNode pre = null;
        while (cur != null) {
            LinkNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        secondHalf = pre;
//        printLinkNode(firstHalf);
//        printLinkNode(pre);

        //合并
        LinkNode dommynode = new LinkNode(0);
        LinkNode currentnode = dommynode;
        while (secondHalf != null && firstHalf != null) {
            if (secondHalf.value <= firstHalf.value) {
                currentnode.next = secondHalf;
                secondHalf = secondHalf.next;
            } else {
                currentnode.next = firstHalf;
                firstHalf = firstHalf.next;
            }
            currentnode = currentnode.next;
        }
        if (firstHalf != null) {
            currentnode.next = firstHalf;
        } else {
            currentnode.next = secondHalf;
        }
        printLinkNode(dommynode.next);
    }


    public static void main(StringPractice[] args) {
        LinkedListProblems listpro = new LinkedListProblems();

        //test：创建链表，打印链表
        //输出  1->3->5->6->null
        LinkNode head1 = listpro.creatLinkNode(new int[]{1, 3, 5, 6});

        listpro.printLinkNode(head1);

        //test：反转链表
        //输出  6->5->3->1->null
        listpro.printLinkNode(listpro.reverseList(head1));

        //test：找链表中点
        //输出  2  2
        LinkNode head2 = listpro.creatLinkNode(new int[]{0, 2, 4, 5});
        System.out.println(listpro.middleNode1(head2).value);
        System.out.println(listpro.middleNode2(head2).value);

        //test：合并链表
        //输出  1->2->3->4->5->6->7->8->null
        LinkNode head3 = listpro.creatLinkNode(new int[]{1, 3, 5, 7});
        LinkNode head4 = listpro.creatLinkNode(new int[]{2, 4, 6, 8});
        listpro.printLinkNode(listpro.mergeTwoLists(head3, head4));

        //test：重排链表
        //输出  1->8->2->7->3->6->4->5->null
        LinkNode head5 = listpro.creatLinkNode(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        listpro.reorderLink(head5);
        listpro.printLinkNode(head5);

        //test：链表升序
        //输出  1->2->3->4->5->null
        LinkNode h = listpro.creatLinkNode(new int[]{1, 5, 2, 4, 3});
        listpro.reorderList1(h);
    }
}
