// MyAidl.aidl
package com.example.aidl_server;
import com.example.aidl_server.Book;
// Declare any non-default types here with import statements

interface MyAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void test_Service();

    //所有的返回值前都不需要加任何东西，不管是什么数据类型
      List<Book> getBooks();

        //传参时除了Java基本类型以及String，CharSequence之外的类型
        //都需要在前面加上定向tag，具体加什么量需而定
      Book addBook(in Book book);
}