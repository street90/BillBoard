# BillBoard

这是模仿淘宝Android首页上下滚动广告牌的一个自定义控件

效果图
---
<img src="https://github.com/street90/BillBoard/blob/master/BillBoard/pic/pic1.gif" width="270" height="450"/>


###XML
	   <com.song.billboardlibrary.BillTextView
        android:layout_centerInParent="true"
        android:id="@+id/btv"
        app:billTextBackground="@android:color/holo_blue_light"
        app:billTextColor="@android:color/holo_orange_light"
        app:billTextSize="17"
        app:billTextGravity="right"
        android:layout_width="match_parent"
        android:layout_height="50dp"/>

在xml中可以设置背景、文字颜色、文字大小和文字的位置

###JAVA
	1. startSmooth();  自动滚动
	2. start();  单次滚动  
	3. setBillText(mStrings)  设置显示的内容List<String>


如果您有什么建议或者意见请联系我
---
* 我的Email : songlang90@outlook.com


License
----------
**WTFPL** 
