package com.example.spritandroidimals.Animations;

/**
 * Runs the logic for the game
 *
 * @author Ben Kugler, Jon Forster, Jonah Pincetich, Kyle Pestka
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.spritandroidimals.GameEvents.Collision;
import com.example.spritandroidimals.GameEvents.EndGame;
import com.example.spritandroidimals.GameEvents.Sounds;
import com.example.spritandroidimals.R;
import com.example.spritandroidimals.Sprites.AnimatedSprite;
import com.example.spritandroidimals.Sprites.Background;
import com.example.spritandroidimals.Sprites.Foreground;
import com.example.spritandroidimals.Sprites.Ground;
import com.example.spritandroidimals.Sprites.Player;
import com.example.spritandroidimals.Sprites.SpriteObjects;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for game logic
 */
public class GraphicsView extends SurfaceView implements Runnable {
    //Hero Constants
    private static final int FIRST = 0;
    private static final int HERO_WIDTH = 1000;
    private static final int HERO_HEIGHT = 1000;
    private static final int HERO_TIME = 0;
    private final static int ROW = 4;
    private final static int COL = 2;
    private final static int MIDGROUND_HEIGHT = 500;
    private final static int MIDGROUND_WIDTH = 800;
    private final static String LEVEL = "level";
    private final static String PIT = "pit";
    private final static String TRAP = "trap";
    private final static String PLESSY = "plessy";
    private final static String LOG = "log";
    private final static String WIRE = "barb";

    //Hero variables
    private int mHeroStartX = 0;
    private int mHeroStartY = 0;

    //Objects Y
    private int mFloorObjectsY;
    private int mObstacleObjectsY;
    private int mObstacleObjectsYHalf;
    private int mForegroundObjectsY;
    private int mMiddlegroundObjectsY;
    private int mNewLevelHeight;
    private int mHeroNextLevel;

    //Objects scale
    private int mSpritesHeight = 150;
    private int mSpritiesWidth = 150;
    private int mGameSpeed = 30;

    //Foreground and Background indices
    private int mMidRandom;
    private int mForeRandom;

    //Game Thread
    private Thread mGameThread = null;

    //SurfaceHolder to paint too
    private SurfaceHolder mHolder;

    //User is playing: volatile means it will be used by different threads!
    private volatile boolean mbPlaying;

    //Canvas and paint
    private Context mContex;
    private Canvas mCanvas;
    private Paint mPaint;
    private Display mDisplay;

    //Calculate FPS and restart
    private long mFPS;
    private long mStartTime;
    private long mSavedTime;
    private boolean mbRestart = false;
    private int count = 0;

    //Background
    private Background mBackground;

    //Game Objects
    private static Collision mcCollision = new Collision();
    private EndGame mGameEnd;
    private Sounds cSounds;
    private ArrayList<Ground> mGround;
    private ArrayList<Foreground> mForeground;
    private ArrayList<Foreground> mMiddleground;
    private ArrayList<SpriteObjects> mSpriteObjects;
    private ArrayList<AnimatedSprite> mAnimatedObjects;
    private ArrayList<SpriteObjects> mNextLevelGround;
    private List<Integer> mLevel;
    private Player mHero;

    boolean mbDefault = true;

    /**
     * Constructor that initializes the values associated with the sprite
     *
     * @param context - Reference to the application resources
     * @param display - The display
     */
    public GraphicsView(Context context, Display display, boolean bDefault)
            throws IOException {
        super(context);
        mContex = context;
        mDisplay = display;
        mbDefault = bDefault;

        //Initializes our canvas and holder
        mHolder = getHolder();
        mPaint = new Paint();
        cSounds = new Sounds(mContex);
        mGameEnd = new EndGame();

        //Set playing to true
        mbPlaying = false;
        mStartTime = SystemClock.currentThreadTimeMillis();
        //mGameThread.start ();

        //Set game speed
        SpriteObjects.setSpeed(mGameSpeed);
        AnimatedSprite.setSpeed(mGameSpeed);
        Ground.setSpeed(mGameSpeed);

        //Initializes the array lists
        mGround = new ArrayList<>();
        mSpriteObjects = new ArrayList<>();
        mAnimatedObjects = new ArrayList<>();
        mForeground = new ArrayList<>();
        mMiddleground = new ArrayList<>();
        mLevel = new ArrayList<>();
        mNextLevelGround = new ArrayList<>();

        //Heights
        mHeroStartX = (int) (getDisplayWidth() * .1);
        mHeroStartY = (int) (getDisplayHeight() * .75);
        mFloorObjectsY = (int) (getDisplayHeight() * .92);
        mObstacleObjectsY = (int) (getDisplayHeight() * .82);
        mObstacleObjectsYHalf = (int) (getDisplayHeight() * .85);
        mForegroundObjectsY = (int) (getDisplayHeight() * .2);
        mMiddlegroundObjectsY = (int) (getDisplayHeight() * .6);
        mObstacleObjectsYHalf = (int) (getDisplayHeight() * .86);
        mHeroNextLevel = (int) (getDisplayHeight() * .65);
        mNewLevelHeight = (int) (getDisplayHeight() * .745);

        //Background
        mBackground = new Background(context, mDisplay, R.drawable.mount, 0, 0,
                getDisplayHeight(), getDisplayWidth(), 0);

        //Creates scrolling ground
        for (int i = 0; i < 10; i++) {
            mGround.add(
                    new Ground(context, mDisplay, R.drawable.grass, 0, mFloorObjectsY,
                            120, 300, 0, i));
        }

        //Foreground
        mMiddleground.add(
                new Foreground(context, mDisplay, R.drawable.snowcliff_1, 0,
                        mMiddlegroundObjectsY, MIDGROUND_HEIGHT, MIDGROUND_WIDTH));
        mMiddleground.add(
                new Foreground(context, mDisplay, R.drawable.snowcliff_2, 0,
                        mMiddlegroundObjectsY, MIDGROUND_HEIGHT, MIDGROUND_WIDTH));
        mForeground.add(new Foreground(context, mDisplay, R.drawable.tree_1, 0,
                mForegroundObjectsY));
        mForeground.add(new Foreground(context, mDisplay, R.drawable.tree_2, 0,
                mForegroundObjectsY));
        mForeground.add(new Foreground(context, mDisplay, R.drawable.tree_3, 0,
                mForegroundObjectsY));
        mForeground.add(new Foreground(context, mDisplay, R.drawable.tree_4, 0,
                mForegroundObjectsY));

        //Loads map from File
        createMap(context, mDisplay);

        //Creates the hero
        mHero = new Player(context, mDisplay, R.drawable.foxyfox, mHeroStartX,
                mHeroStartY, HERO_HEIGHT, HERO_WIDTH, HERO_TIME, ROW, COL);
        mHero.setJump(true);
    }

    /**
     * Creates the user map
     *
     * @param context  - The current resources
     * @param mDisplay - The current display
     */
    public void createMap(Context context, Display mDisplay) throws IOException {
        String[] mapTimeStamps;
        FileInputStream file;
        String temp = "";
        int c;

        if (!mbDefault) {
            file = getContext().openFileInput("map");
            while ((c = file.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
        } else {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.map);

            BufferedReader r = new BufferedReader(new InputStreamReader(in_s));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            temp = total.toString();
        }

        mapTimeStamps = temp.split(",");

        Log.d("TIME ARRAY LENGTH", "LENGH:   " + mapTimeStamps.length);

        for (int k = 0; k < mapTimeStamps.length; k += 2) {
            if (mapTimeStamps[k].equals(TRAP)) {
                mSpriteObjects.add(
                        new SpriteObjects(context, mDisplay, R.drawable.trap,
                                getDisplayWidth(), mObstacleObjectsYHalf, (mSpritesHeight / 2),
                                mSpritiesWidth, Integer.parseInt(mapTimeStamps[k + 1])));
            }
            if (mapTimeStamps[k].equals(LOG)) {
                mSpriteObjects.add(
                        new SpriteObjects(context, mDisplay, R.drawable.log,
                                getDisplayWidth(), mObstacleObjectsY, mSpritesHeight,
                                mSpritiesWidth, Integer.parseInt(mapTimeStamps[k + 1])));
            }
            if (mapTimeStamps[k].equals(WIRE)) {
                mSpriteObjects.add(
                        new SpriteObjects(context, mDisplay, R.drawable.bardwire,
                                getDisplayWidth(), mObstacleObjectsY, mSpritesHeight,
                                mSpritiesWidth, Integer.parseInt(mapTimeStamps[k + 1])));
            }
            if (mapTimeStamps[k].equals(PIT)) {
                mSpriteObjects.add(
                        new SpriteObjects(context, mDisplay, R.drawable.pit,
                                getDisplayWidth(), mFloorObjectsY - 18, mSpritesHeight * 2,
                                mSpritiesWidth + 250, Integer.parseInt(mapTimeStamps[k + 1])));
            }
            if (mapTimeStamps[k].equals(PLESSY)) {
                mAnimatedObjects.add(
                        new AnimatedSprite(context, mDisplay, R.drawable.plessy,
                                getDisplayWidth(), (int) (getDisplayHeight() * .83), 1000,
                                1000, Integer.parseInt(mapTimeStamps[k + 1]), 8, 8));
            }
            if (mapTimeStamps[k].equals(LEVEL)) {
                mLevel.add(Integer.parseInt(mapTimeStamps[k + 1]));
                mLevel.add(Integer.parseInt(mapTimeStamps[k + 2]));
                k = +1;
            }
        }
    }

    /**
     * This will run our game, pausing will be done here and frames
     */
    @Override
    public void run() {
        final int TO_SEC = 1000;
        final int NO_FRAME = 0;
        Random rn = new Random();

        while (mbPlaying) {
            long startFrame = SystemClock.currentThreadTimeMillis();

            //Randomizes which middleground and foreground images to draw

            for (int i = 0; i < mForeground.size(); i++) {
                if (mForeground.get(i).getRandom()) {
                    mForeRandom = rn.nextInt(mForeground.size());
                    mForeground.get(i).randomize();
                }
            }

            for (int i = 0; i < mMiddleground.size(); i++) {
                if (mMiddleground.get(i).getRandom()) {
                    mMidRandom = rn.nextInt(mMiddleground.size());
                    mMiddleground.get(i).randomize();
                }
            }

            //Updates the game and moves objects
            update();

            //Draws the frame
            draw();

            //Gets the frames,  start time minus end time and convert to seconds
            long mTimeFrame;
            if (!mbRestart) {
                mTimeFrame = SystemClock.currentThreadTimeMillis() - startFrame;
            } else {
                mTimeFrame = SystemClock.currentThreadTimeMillis() - mSavedTime;
            }

            if (mTimeFrame > NO_FRAME) {
                mFPS = TO_SEC / mTimeFrame;
            }
        }
    }

    /**
     * This will update things like X and Y of objects
     */
    public void update() {
        //Draw middleground
        mMiddleground.get(mMidRandom).update();

        //Checks to new floor heights
        for (int i = 0; i < mLevel.size(); i += 2) {
            if (SystemClock.currentThreadTimeMillis() >= mLevel.get(i)) {
                count++;
                //Update the floor level
                updateLevel(mLevel.get(i), mLevel.get(i + 1));
            }
        }

        for (int i = 0; i < (mLevel.size() / 2); i++) {
            if (SystemClock.currentThreadTimeMillis() >= mLevel.get(i)) {
                if (SystemClock.currentThreadTimeMillis() <= mLevel.get(i + 1)) {
                    mHero.setJumpYstart(mHeroNextLevel);
                }
            }
        }

        for (int i = 0; i < (mLevel.size() / 2); i++) {
            if (SystemClock.currentThreadTimeMillis() >= mLevel.get(i)) {
                if (SystemClock.currentThreadTimeMillis() >= mLevel.get(i + 1)) {
                    mHero.setJumpYstart(mHeroStartY);
                }
            }
        }

        //Update Hero
        mHero.update();

        //Update ground
        for (int i = 0; i < mGround.size(); i++) {
            mGround.get(i).update();
        }

        //Update Static sprites
        for (int i = 0; i < mSpriteObjects.size(); i++) {
            mSpriteObjects.get(i).update(mFPS);

            if (mcCollision.collisionDetection(mHero, mSpriteObjects.get(i))) {
                int id = mSpriteObjects.get(i).getSpriteID();
                if (id == R.drawable.log) {
                    cSounds.playLog();
                } else if (id == R.drawable.trap) {
                    cSounds.playTrap();
                } else if (id == R.drawable.bardwire) {
                    cSounds.playWire();
                } else if (id == R.drawable.pit) {
                    cSounds.playPit();
                } else if (id == R.drawable.plessy) {
                    cSounds.playPlessy();
                }

                mbPlaying = false;
                mGameEnd.endGame(mContex);
            }
        }
        //Update Animated sprites
        for (int i = 0; i < mAnimatedObjects.size(); i++) {
            mAnimatedObjects.get(i).update(mFPS);
            if (mcCollision.collisionDetection(mHero, mAnimatedObjects.get(i))) {

                mbPlaying = false;
            }
        }

        //Draw foreground
        mForeground.get(mForeRandom).update();

        for (int i = 0; i < mNextLevelGround.size(); i++) {
            mNextLevelGround.get(i).update(mFPS);
        }
    }

    /**
     * If there is a new level, move objects
     */
    void updateLevel(int timeStampStart, int timeStampeEnd) {
        // If we one another height, change the starting Y of objects
        for (int i = 0; i < mSpriteObjects.size(); i++) {
            if (mSpriteObjects.get(i).getTimeToDraw() >= timeStampStart) {
                if (mSpriteObjects.get(i).getTimeToDraw() <= timeStampeEnd) {
                    mSpriteObjects.get(i).setSpriteYLeft(mNewLevelHeight);
                }
            }
        }

        for (int i = 0; i < mAnimatedObjects.size(); i++) {
            if (mAnimatedObjects.get(i).getTimeToDraw() >= timeStampStart) {
                if (mAnimatedObjects.get(i).getTimeToDraw() <= timeStampeEnd) {
                    mAnimatedObjects.get(i).setSpriteYLeft(mNewLevelHeight);
                }
            }
        }

        if (SystemClock.currentThreadTimeMillis() >= timeStampStart) {
            if (SystemClock.currentThreadTimeMillis() <= timeStampeEnd) {
                if (count % 10 == 0) {
                    mNextLevelGround.add(
                            new SpriteObjects(mContex, mDisplay, R.drawable.topground,
                                    getDisplayWidth(), mFloorObjectsY - 125, 240, 300, 0));
                }
            }
        }

        for (int i = 0; i < mNextLevelGround.size(); i++) {
            if (mcCollision.collisionDetection(mHero, mNextLevelGround.get(i))) {
                mHero.setJumpYstart(mHeroNextLevel);
                mHero.setJump(false);
            }
        }
    }

    /**
     * Draws everything to the screen and that's it
     */
    public void draw() {
        //Checks to make sure the surface is valid
        if (mHolder.getSurface().isValid()) {
            //Lock tha canvas so we can draw
            //Draw to the canvas
            mCanvas = mHolder.lockCanvas();

            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            mPaint.setColor(Color.argb(255, 0, 255, 0));

            mPaint.setTextSize(60);

            //Background
            mBackground.draw(mCanvas);

            //Display FPS
            mCanvas.drawText("FPS: " + mFPS, 20, 60, mPaint);

            //Draw middleground
            mMiddleground.get(mMidRandom).draw(mCanvas);

            //Draw Hero
            mHero.draw(mCanvas);

            //Draw ground
            for (int i = 0; i < mGround.size(); i++) {
                mGround.get(i).draw(mCanvas);
            }

            //Draw ground
            for (int i = 0; i < mGround.size(); i++) {
                mGround.get(i).draw(mCanvas);
            }

            //Draw Array list of objects
            for (int i = 0; i < mSpriteObjects.size(); i++) {
                mSpriteObjects.get(i).draw(mCanvas);
            }

            //Draw the Animated objects
            for (int i = 0; i < mAnimatedObjects.size(); i++) {
                mAnimatedObjects.get(i).draw(mCanvas);
            }
            for (int i = 0; i < mNextLevelGround.size(); i++) {
                mNextLevelGround.get(i).draw(mCanvas);
            }
            //Draw Foreground
            mForeground.get(mForeRandom).draw(mCanvas);

            //Draws everything to the screen and unlocks drawing surface
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * Gets onTouch events, only for causing character to jump
     *
     * @param ev - The event
     * @return - Returns false, Stops getting touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!mHero.getJump()) {
            cSounds.playJump();
            mHero.setJump(true);
        }

        return false;
    }

    /**
     * Pause the game and try to shutdown out game thread
     */
    public void pause() {
        mbPlaying = false;

        try {
            mGameThread.join();

        } catch (InterruptedException e) {
            Log.e("Error!!!", "Failed to join (Kill) Game Thread");
        }
    }

    /**
     * If the game is started, start out thread
     */
    public void resume() {
        mbPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    /**
     * gets the displays height
     *
     * @return - display height
     */
    public int getDisplayHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Gets the display width
     *
     * @return - display width
     */
    public int getDisplayWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}
