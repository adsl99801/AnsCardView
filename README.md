Used in the exam answer card
##first
Add dependency
```groovy
dependencies {
          compile 'com.lfo.anscard:anscard:v1.0.1'
}
```
#second
-activity_main.xml
```java 
 <ScrollView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/scrollView" >

        <LinearLayout
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <com.lfo.anscard.AnsCardView
                android:id="@+id/ansCardView"

                android:layout_width="wrap_content"
                android:layout_height="wrap_content" />

        </LinearLayout>
    </ScrollView>
```
#done
-MainActivity.class
```java 
   @Bind(R.id.ansCardView)
   AnsCardView ansCardView;
   //AnsCardTool is used to  generate any data you want in anscardView
   ansCardView.setDrawListAndCodeList(AnsCardTool.generate(), AnsCardTool.generateCodeList());
```


