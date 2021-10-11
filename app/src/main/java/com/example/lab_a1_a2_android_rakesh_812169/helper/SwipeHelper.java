package com.example.lab_a1_a2_android_rakesh_812169.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.kevincodes.recyclerview.ItemDecorator;


public class SwipeHelper extends ItemTouchHelper.Callback {
    private final SwipeInterface adapter;
    private Context context;

    public SwipeHelper(SwipeInterface adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        adapter.onItemSwiped(position, direction);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        Paint p = new Paint();
        if (dX > 0) {
            c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), p);
        } else {
            c.drawRect((float) itemView.getRight(), (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), p);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int red = ContextCompat.getColor(context, R.color.red);
        int yellow = ContextCompat.getColor(context, R.color.yellow);
        int black = ContextCompat.getColor(context, R.color.black);
        int white = ContextCompat.getColor(context, R.color.white);

        new ItemDecorator.Builder(c, recyclerView, viewHolder, dX, actionState)
                .setFromStartToEndIconTint(white)
                .setFromEndToStartIconTint(white)
                .setFromStartToEndTypeface(Typeface.DEFAULT_BOLD)
                .setFromEndToStartTypeface(Typeface.SANS_SERIF)
                .setFromStartToEndTextSize(25, 16)
                .setFromEndToStartTextSize(16, 16)
                .setFromStartToEndTextColor(black)
                .setFromEndToStartTextColor(black)
                .setFromEndToStartIcon(R.drawable.twotone_delete_24)
                .setFromStartToEndText("Delete")
                .setFromEndToStartBgColor(red)
                .setFromStartToEndIcon(R.drawable.twotone_edit_24)
                .setFromStartToEndText("Edit")
                .setFromStartToEndBgColor(yellow)
                .create()
                .decorate();
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
