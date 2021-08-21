package com.jakimenko.rainforecastbot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

//@SpringBootTest
class RainforecastbotApplicationTests {

//	@Test
	fun contextLoads() {
		println("start")
		val res = ways(4, 2)
		println(res)
	}

	fun ways(total: Int, k: Int): Int {
		val arr = IntArray(k) { 0 }
		arr[0] = total
		val q: ArrayDeque<Node> = ArrayDeque()
		val root = Node(arr)
		q.addFirst(root)
		val set: MutableSet<Node> = HashSet()
		set.add(root)

		while (q.isNotEmpty()) {
			val len = q.size

			for (i in 0 until len) {
				val node = q.removeFirst()
				if (node.arr[0] > 1) {
					for (j in 1 until k) {
						val newArr = node.arr.copyOf()
						newArr[0] = newArr[0] - (j+1)
						newArr[j] = newArr[j] + 1
						if (check(newArr, k, total)) {
							val newNode = Node(newArr)
							q.addLast(newNode)
							set.add(newNode)
						}
					}
				}
			}
		}
		return set.size
	}

	fun check(arr: IntArray, len: Int, total: Int):Boolean {
		if (arr[0] < 0)
			return false
		var sum = 0
		for (i in 0 until len) {
			sum += (i+1) * arr[i]
		}
		return sum == total
	}


	class Node(
		val arr: IntArray

	) {
		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other !is Node) return false

			if (!arr.contentEquals(other.arr)) return false

			return true
		}

		override fun hashCode(): Int {
			return arr.contentHashCode()
		}
	}

}
