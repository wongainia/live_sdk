package com.lib.showfield.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.TextView;

import com.lib.showfield.http.glide.GlideApp;


/**
 * Created by upingu
 * Date  2020-08-07 14:06
 * <p>
 * Description
 **/
public class URLImageParser {

    private Context mContext;
    private TextView mTextView;
    private int mImageSize;

    /**
     * @param textView  图文混排TextView
     * @param context
     * @param imageSize 图片显示高度
     */
    public URLImageParser(TextView textView, Context context, int imageSize) {
        mTextView = textView;
        mContext = context;
        mImageSize = imageSize;
    }

    public Drawable getDrawable(String url) {
        URLDrawable urlDrawable = new URLDrawable();
        new ImageGetterAsyncTask(mContext, url, urlDrawable).execute(mTextView);
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {

        private URLDrawable urlDrawable;
        private Context context;
        private String source;
        private TextView textView;

        public ImageGetterAsyncTask(Context context, String source, URLDrawable urlDrawable) {
            this.context = context;
            this.source = source;
            this.urlDrawable = urlDrawable;
        }

        @Override
        protected Bitmap doInBackground(TextView... params) {
            textView = params[0];
            try {
                //下载网络图片，以下是使用Picasso和Glide获取网络图片例子，也可以其他方式下载网络图片

                // 使用Picasso获取网络图片Bitmap
                //return Picasso.with(context).load(source).get();
                // 使用Glide获取网络图片Bitmap(使用Glide获取图片bitmap还有待研究)
                return GlideApp.with(context).asBitmap().load(source).fitCenter().into(mImageSize * 3, mImageSize * 3).get();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            try {
                //获取图片宽高比
                float ratio = bitmap.getWidth() * 1.0f / bitmap.getHeight();
                Drawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                bitmapDrawable.setBounds(0, 0, (int) (mImageSize * ratio), mImageSize);
                //设置图片宽、高（这里传入的mImageSize为字体大小，所以，设置的高为字体大小，宽为按宽高比缩放）
                urlDrawable.setBounds(0, 0, (int) (mImageSize * ratio), mImageSize);
                urlDrawable.drawable = bitmapDrawable;
                //两次调用invalidate才会在异步加载完图片后，刷新图文混排TextView，显示出图片
                urlDrawable.invalidateSelf();
                textView.invalidate();
            } catch (Exception e) {
                /* Like a null bitmap, etc. */
            }
        }
    }
}