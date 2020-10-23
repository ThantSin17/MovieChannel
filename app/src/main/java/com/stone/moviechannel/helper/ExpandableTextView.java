package com.stone.moviechannel.helper;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class ExpandableTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "ExpandableTextView";
    private static final String ELLIPSIZE = "... ";
    private static final String MORE = "more";
    private static final String LESS = "less";

    private String mFullText;
    private int mMaxLines;

    public ExpandableTextView(Context context) {
        super(context);
    }

    //...constructors...

    public void makeExpandable(String fullText, int maxLines) {
        mFullText =fullText;
        mMaxLines = maxLines;
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (getLineCount() <= mMaxLines) {
                    setText(mFullText);
                } else {
                    setMovementMethod(LinkMovementMethod.getInstance());
                    showLess();
                }
            }
        });
    }

    /**
     * truncate text and append a clickable {@link #MORE}
     */
    private void showLess() {
        int lineEndIndex = getLayout().getLineEnd(mMaxLines - 1);
        String newText = mFullText.substring(0, lineEndIndex - (ELLIPSIZE.length() + MORE.length() + 1))
                + ELLIPSIZE + MORE;
        SpannableStringBuilder builder = new SpannableStringBuilder(newText);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showMore();
            }
        }, newText.length() - MORE.length(), newText.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }

    /**
     * show full text and append a clickable {@link #LESS}
     */
    private void showMore() {
        // create a text like subText + ELLIPSIZE + MORE
        SpannableStringBuilder builder = new SpannableStringBuilder(mFullText + LESS);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showLess();
            }
        }, builder.length() - LESS.length(), builder.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }
}