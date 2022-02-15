package com.example.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidl_server.MyAidl;
import com.example.aidl_server.Book;


public class MainActivity extends AppCompatActivity {

    private Button bindService;

    //定义aidl接口变量
    private MyAidl mAIDL_Service;

    //创建ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //在Activity与Service建立关联时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //使用AIDLService1.Stub.asInterface()方法将传入的IBinder对象传换成了mAIDL_Service对象
            mAIDL_Service = MyAidl.Stub.asInterface(service);

            try {

                //通过该对象调用在MyAIDLService.aidl文件中定义的接口方法,从而实现跨进程通信
                mAIDL_Service.test_Service();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private Button bind_service2;
    private Button bind_service3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindService = (Button) findViewById(R.id.bind_service);
        bind_service2 = (Button) findViewById(R.id.bind_service2);
        bind_service3 = (Button) findViewById(R.id.bind_service3);

        //设置绑定服务的按钮
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("点击了[绑定服务]按钮");

                //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
                //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
                Intent intent = new Intent("scut.carson_ho.service_server.AIDL_Service1");

                //Android5.0后无法只通过隐式Intent绑定远程Service
                //需要通过setPackage()方法指定包名
                intent.setPackage("com.example.aidl_server");

                //绑定服务,传入intent和ServiceConnection对象
                bindService(intent, connection, Context.BIND_AUTO_CREATE);

            }
        });

        bind_service2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("bookTest");
                Log.e("xxxxBook",book.getName());
                try {
                    mAIDL_Service.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        bind_service3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = null;
                try {
                    book = mAIDL_Service.getBooks().get(0);
                    Log.e("getBook",book.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
