package com.ices507.troy.ivalue_clock.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by troy on 17-12-21.
 *
 * @Description:
 * @Modified By:
 */

public class CustomTableView extends TableLayout
{

    protected int mColumnN=3;//列的数目。该值只能在构造函数中设置，设置之后不能修改。

    int mLineColor=Color.BLACK;//线的颜色
    int mLineWidth=1;//线宽

    protected List<TableRow> mRows;
    protected List<List<View>> mViews;

    public int getmColumnN() {
        return mColumnN;
    }

    public CustomTableView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mRows=new ArrayList<TableRow>();
        mViews=new ArrayList<List<View>>();
        this.setWillNotDraw(false);
    }
    public CustomTableView(Context context, int n) {//指定列的数目
        super(context);
        // TODO Auto-generated constructor stub
        mRows=new ArrayList<TableRow>();
        mViews=new ArrayList<List<View>>();
        if(n>0) mColumnN=n;
        else mColumnN=2;
        this.setWillNotDraw(false);
    }

    public void clearRows()
    {
        if(mRows != null) mRows.clear();
        if(mViews != null) mViews.clear();
        mRows=new ArrayList<TableRow>();
        mViews=new ArrayList<List<View>>();

        this.removeAllViews();
    }

    public int addRow(java.lang.Object objects[])//添加一行，返回行数。如果objects的数目小于mColumnN则返回0。
    {
        if(objects==null) return 0;
        if(objects.length<mColumnN) return 0;

        List<View> cRowViews=new ArrayList<View>();
        int i,nRows;
        TableRow CRow;
        String s1 = null,ss[]={" "};
        View v1=null;

        mRows.add(new TableRow(this.getContext()));
        mViews.add(new ArrayList<View>());
        nRows=mRows.size();
        cRowViews=mViews.get(nRows-1);
        CRow=mRows.get(nRows-1);

        for(i=0;i<mColumnN;i++)
        {
            if(objects[i] != null) v1= createCellView(objects[i]);
            if(v1 == null) v1=new View(getContext());
            CRow.addView(v1);
            cRowViews.add(v1);
        }
        this.addView(CRow);

        return nRows;
    }

    public View getCellView(int row, int column)//获得某一个单元格的View，row为行数，column为列数，从0开始
    {
        if (row < 0 || row >= mRows.size()) {
            return null;
        } else {
            if (column < 0 || column >= mViews.get(row).size()) return null;
            else return mViews.get(row).get(column);
        }
    }

    protected View createCellView(Object obj)//根据obj的类型创建一个VIEW并返回之，如果无法识别Object的类型返回null
    {
        View rView=null;
        String classname = obj.getClass().toString();

        switch (classname)
        {
            case "class java.lang.String":
            case "class java.lang.Integer":
            case "class java.lang.Double":
                TextView tView=new TextView(getContext());
                tView.setText(obj.toString());
                rView=tView;
                break;

            case "class android.graphics.Bitmap":
                ImageView iView=new ImageView(getContext());
                iView.setImageBitmap((Bitmap) obj);
                rView=iView;
                break;

            //在此处识别其它的类型，创建一个View并附给rView

            default:
                rView=null;
                break;
        }
        return rView;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //
        if(mRows.size()<1) return;

        Paint paint1=new Paint();
        int i,nRLinePosition=0,nCLinePosition=0,width=getWidth(),height=getHeight();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(mLineWidth);
        paint1.setColor(mLineColor);

        canvas.drawRect(new Rect(1, 1, width, height), paint1);

        for(i=0;i<mRows.size();i++)
        {
            nRLinePosition+=mRows.get(i).getHeight();
            canvas.drawLine(0, nRLinePosition, width, nRLinePosition, paint1);
        }
        for(i=0;i<mViews.get(0).size();i++)
        {
            nCLinePosition+=mViews.get(0).get(i).getWidth();
            canvas.drawLine(nCLinePosition, 0, nCLinePosition, height, paint1);
        }
    }
}