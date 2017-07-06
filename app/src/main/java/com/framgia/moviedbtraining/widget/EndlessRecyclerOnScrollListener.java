package com.framgia.moviedbtraining.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    // The total number of items in the dataset after the last load
    private int mPreviousTotal = 0;
    private boolean isLoading = true;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;

    public EndlessRecyclerOnScrollListener() {
    }

    @Override
    public void onScrolled(RecyclerView recycler, int dx, int dy) {
        super.onScrolled(recycler, dx, dy);
        mVisibleItemCount = recycler.getChildCount();
        mTotalItemCount = recycler.getLayoutManager().getItemCount();
        if (recycler.getLayoutManager() instanceof LinearLayoutManager) {
            mFirstVisibleItem =
                    ((LinearLayoutManager) recycler.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            mFirstVisibleItem =
                    ((GridLayoutManager) recycler.getLayoutManager()).findFirstVisibleItemPosition();
        } else {
            throw new RuntimeException("Un support this kind of LayoutManager ");
        }

        if (isLoading) {
            stateLoading();
        }

        if (!isLoading && (mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem + 3)) {
            // End has been reached
            onLoadMore();
            isLoading = true;
        }
    }

    private void stateLoading() {
        if (mTotalItemCount > mPreviousTotal) {
            isLoading = false;
            mPreviousTotal = mTotalItemCount;
        }
    }

    public void resetOnLoadmore() {
        mFirstVisibleItem = 0;
        mVisibleItemCount = 0;
        mTotalItemCount = 0;
        mPreviousTotal = 0;
        isLoading = true;
    }

    public abstract void onLoadMore();
}