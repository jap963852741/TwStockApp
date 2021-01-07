package com.jap.twstockapp.util

/**
 * The above code is the most efficient code for double-checked locking system and the code is somehow similar to the lazy()
 * function in Kotlin and that’s why it is called lazy initialization.
 *  So, whenever you want a singleton class with arguments then you can use the SingletonHolder class.
*/





open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}