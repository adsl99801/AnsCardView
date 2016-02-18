package com.lfo.anscard;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.gs.collections.impl.list.mutable.FastList;

/**
 * Created by AnhongNB01 on 2016/2/16.
 */
public class AnsCardView extends View {
    private BasePoint p1 = new BasePoint(0, 0);
    private BasePoint p2 = new BasePoint(0, 0);
    private int width = 80;
    private int height = 40;
    private int space = 20;
    private int lineSpace = 8;
    //private int lineHight = height + 20;
    private int lineNo = 0;
    private int strokeWidth = 5;
    private int maxCellInAItem = 5;
    private float cellHeight;
    private float textHeight;
    private Paint cellPaint;
    private Paint textPaint;
    private FastList<Item> drawList = new FastList<>();
    private int lineHeight= lineSpace+strokeWidth+height+strokeWidth;//+lineSpace;
    private int ansColor=android.R.color.holo_red_dark;
    private int nomalColor=android.R.color.black;
    private Context context;

    public AnsCardView(Context context) {
        super(context);
        this.context=context;
    }

    public AnsCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public AnsCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int preferred = maxCellInAItem*width+height+space*maxCellInAItem+height;//"1."寬度為height by setTextSizeForWidth()
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = drawList.size()*lineHeight+lineSpace;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement = 0;

        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                // This means the width of this view has been given.
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                // Take the minimum of the preferred size and what
                // we were told to be.
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }

        return measurement;
    }




    public void setDrawList(FastList<Item> drawList) {
        resetBasePoint();
        this.drawList = drawList;
        requestLayout();
        invalidate();
    }

    private void init() {
        resetBasePoint();
        orepareTextPaint();
        orepareAnsCellPaint();

    }

    private void resetBasePoint() {
        p1.x = 0;
        p1.y = strokeWidth;//否則會頂到頂部
        p2.x = 0;
        p2.y = strokeWidth;
        lineNo = 0;
        textHeight=0;

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        //super.onDraw(canvas);

        if (cellPaint == null) {
            init();
        }
        drawList.forEachWithIndex((item, index) -> {
            FastList<Cell> cells = item.getCells();
            text(canvas, textPaint, String.valueOf(index + 1) + ".");
            cells.forEachWithIndex((cell, yindex) -> {
                nextAnsCell(canvas, cellPaint, yindex, cell);
            });
            br();

        });
    }

    private void orepareTextPaint() {
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        setTextSizeForWidth(textPaint, width, "0000");
    }

    private void orepareAnsCellPaint() {
        cellPaint = new Paint();
        cellPaint.setStyle(Paint.Style.STROKE);
        cellPaint.setStrokeWidth(strokeWidth);
        cellPaint.setColor(ContextCompat.getColor(context, nomalColor));
    }

    private void text(Canvas canvas, Paint textpaint, String text) {

        float x=p1.x;
        if(lineNo<9){
            Rect bounds = new Rect();
            textpaint.getTextBounds(text, 0, text.length(), bounds);
            float centerw=bounds.right;
            x+=centerw;
        }else {
            if(lineNo<99){
                Rect bounds = new Rect();
                textpaint.getTextBounds(text, 0, text.length(), bounds);
                float centerw=bounds.right>>2;
                x+=centerw;
            }
        }

        if(lineNo==0){
            textHeight+=height;
            canvas.drawText(text, x,textHeight, textpaint);
            return;
        }


        textHeight += height+lineSpace;
        canvas.drawText(text, x, textHeight, textpaint);//+
    }

    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {

        // You should set desiredWidth to the width of your canvas. If the result isn't accurate, try increasing the size of testTextSize

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }
    private void br() {
        lineNo++;
        p1.x = 0;
        cellHeight =p1.y+height+lineSpace;
        p1.y = cellHeight;
    }

    private void nextAnsCell(Canvas canvas, Paint paint, int index, Cell cell) {
        if (index == 0) {
            drawNextAnsCell(canvas, paint, cell);
        }
        drawNextAnsCellWithSpace(canvas, paint, cell);
    }

    private void drawNextAnsCellFun(Canvas canvas, Paint paint, Cell cell,float p1x){
        moveP2ByP1();
        if (cell.isPicked()) {
            paint.setStyle(Paint.Style.FILL);
        }
        if(cell.isShowAns()){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(context, ansColor));
        }
        canvas.drawRect(p1x, p1.y, p2.x, p2.y, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(context, nomalColor));
    }
    private void drawNextAnsCell(Canvas canvas, Paint paint, Cell cell) {
        p1.x = p1.x + width;
        drawNextAnsCellFun(canvas,paint,cell,p1.x);
    }

    private void drawNextAnsCellWithSpace(Canvas canvas, Paint paint, Cell cell) {
        p1.x = p1.x + width + space;
        drawNextAnsCellFun(canvas, paint, cell, p1.x);
    }

    private void moveP2ByP1() {
        p2.x = p1.x + width;
        p2.y = p1.y + height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setNomalColor(int nomalColor) {
        this.nomalColor = nomalColor;
    }

    public void setAnsColor(int ansColor) {
        this.ansColor = ansColor;
    }

    static class BasePoint {
        float x;
        float y;

        public BasePoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Item {
        private int no;
        private FastList<Cell> cells = new FastList<>();

        public Item() {
        }

        public Item(FastList<Cell> cells, int no) {
            this.cells = cells;
            this.no = no;
        }

        public FastList<Cell> getCells() {
            return cells;
        }

        public int getNo() {
            return no;
        }
    }

    public static class Cell {
        private int position;
        private String code;
        private boolean picked;
        private boolean showAns;
        public Cell() {
        }


        public Cell(String code, boolean picked, int position, boolean showAns) {
            this.code = code;
            this.picked = picked;
            this.position = position;
            this.showAns = showAns;
        }

        public boolean isShowAns() {
            return showAns;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isPicked() {
            return picked;
        }

        public void setPicked(boolean picked) {
            this.picked = picked;
        }

        public void setShowAns(boolean showAns) {
            this.showAns = showAns;
        }

        public int getPosition() {
            return position;
        }
    }
}