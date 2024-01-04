package com.example.gyrodraw.home.gallery;


import static com.example.gyrodraw.utils.LayoutUtils.bounceButton;
import static com.example.gyrodraw.utils.LayoutUtils.isPointInsideView;
import static com.example.gyrodraw.utils.LayoutUtils.pressButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.gyrodraw.NoBackPressActivity;
import com.example.gyrodraw.R;
import com.example.gyrodraw.localDatabase.LocalDbForImages;
import com.example.gyrodraw.localDatabase.LocalDbHandlerForImages;
import com.example.gyrodraw.utils.GlideUtils;
import com.example.gyrodraw.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the gallery, where users can see the pictures they drew.
 */
public class GalleryActivity extends NoBackPressActivity {

    private static final int COLUMNS = 3;
    public static final String POS = "pos";

    private Dialog confirmationPopup;

    private static List<Bitmap> bitmaps;

    public static List<Bitmap> getBitmaps() {
        return new ArrayList<>(bitmaps);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_gallery);

        GlideUtils.startBackgroundAnimation(this);

        ((TextView) findViewById(R.id.galleryText)).setTypeface(typeMuro);

        confirmationPopup = new Dialog(this);

        TextView exitButton = findViewById(R.id.crossText);

        exitButton.setTypeface(typeMuro);
        ((TextView) findViewById(R.id.galleryText)).setTypeface(typeMuro);
        LayoutUtils.setFadingExitHomeListener(exitButton, this);

        TextView emptyGalleryText = findViewById(R.id.emptyGalleryText);
        emptyGalleryText.setTypeface(typeMuro);

        final RecyclerView recyclerView = findViewById(R.id.galleryList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        recyclerView.setHasFixedSize(true);

        final LocalDbForImages dbHandler = new LocalDbHandlerForImages(this, null, 1);
        bitmaps = dbHandler.getBitmaps(this);

        ImageView deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressButton(view, LayoutUtils.AnimMode.CENTER, GalleryActivity.this);
                        break;
                    case MotionEvent.ACTION_UP:
                        bounceButton(view, GalleryActivity.this);
                        if (isPointInsideView(event.getRawX(), event.getRawY(), view)) {
                            showConfirmationPopup(dbHandler);
                        }
                        break;
                    default:
                }
                return true;
            }
        });

        GalleryAdapter adapter = new GalleryAdapter(this, bitmaps);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(),
                                FullscreenImageActivity.class);
                        intent.putExtra(POS, position);
                        startActivity(intent);
                    }
                }));

        // Hide or display the empty gallery text
        LinearLayout galleryLayout = findViewById(R.id.galleryLinearLayout);
        galleryLayout.removeView(bitmaps.isEmpty() ? recyclerView : emptyGalleryText);
    }

    private void showConfirmationPopup(final LocalDbForImages dbHandler) {
        confirmationPopup.setContentView(R.layout.delete_images_confirmation_pop_up);

        final Context context = this;
        confirmationPopup.findViewById(R.id.yesButton)
                .setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                pressButton(view, LayoutUtils.AnimMode.CENTER, context);
                                break;
                            case MotionEvent.ACTION_UP:
                                bounceButton(view, context);
                                dbHandler.removeAll();
                                confirmationPopup.dismiss();
                                recreate();
                                break;
                            default:
                        }
                        return true;
                    }
                });

        confirmationPopup.findViewById(R.id.noButton)
                .setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                pressButton(view, LayoutUtils.AnimMode.CENTER, context);
                                break;
                            case MotionEvent.ACTION_UP:
                                bounceButton(view, context);
                                confirmationPopup.dismiss();
                                break;
                            default:
                        }
                        return true;
                    }
                });

        confirmationPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmationPopup.show();
    }

    /**
     * A {@link RecyclerView.Adapter} used to manage the different pictures in the gallery.
     */
    private class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final Context context;
        private final List<Bitmap> images;

        private GalleryAdapter(Context context, List<Bitmap> images) {
            this.context = context;
            this.images = new ArrayList<>(images);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.gallery_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Glide.with(context).load(images.get(position))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(new RequestOptions()
                            .override(200, 200)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .thumbnail(0.5f)
                    .into(((ItemHolder) holder).imageView);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }

    /**
     * A {@link RecyclerView.ViewHolder} used to hold an {@link ImageView}.
     */
    private static class ItemHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        private ItemHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
        }
    }
}
