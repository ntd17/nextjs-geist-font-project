package com.yourcompany.canvasanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CanvasAnimationView extends View {

    private Bitmap[] frames;
    private int currentFrameIndex = 0;
    private AnimationThread animationThread;
    private boolean running = false;
    private final int frameDelay = 100; // milliseconds between frames

    public CanvasAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Load your image sequence (example using drawables)
        frames = new Bitmap[] {
                BitmapFactory.decodeResource(getResources(), R.drawable.frame1),
                BitmapFactory.decodeResource(getResources(), R.drawable.frame2),
                BitmapFactory.decodeResource(getResources(), R.drawable.frame3)
                // Add more frames as needed
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (frames != null && frames.length > 0) {
                // Draw the current frame centered
                Bitmap currentFrame = frames[currentFrameIndex];
                int left = (getWidth() - currentFrame.getWidth()) / 2;
                int top = (getHeight() - currentFrame.getHeight()) / 2;
                canvas.drawBitmap(currentFrame, left, top, null);
            }
        } catch (Exception e) {
            Log.e("CanvasAnimationView", "Error in onDraw", e);
        }
    }

    public void startAnimation() {
        if (animationThread == null || !running) {
            running = true;
            animationThread = new AnimationThread();
            animationThread.start();
        }
    }

    public void pauseAnimation() {
        running = false;
    }

    public void resumeAnimation() {
        if (!running) {
            running = true;
            if (animationThread == null || !animationThread.isAlive()) {
                animationThread = new AnimationThread();
                animationThread.start();
            }
        }
    }

    public void resetAnimation() {
        running = false;
        currentFrameIndex = 0;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        running = false;
        super.onDetachedFromWindow();
    }

    private class AnimationThread extends Thread {
        @Override
        public void run() {
            while (running) {
                try {
                    currentFrameIndex = (currentFrameIndex + 1) % frames.length;
                    postInvalidate(); // request UI thread to redraw
                    Thread.sleep(frameDelay);
                } catch (InterruptedException e) {
                    Log.e("AnimationThread", "Animation thread interrupted", e);
                    running = false;
                }
            }
        }
    }
}
