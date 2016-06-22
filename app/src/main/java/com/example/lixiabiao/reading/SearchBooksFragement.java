package com.example.lixiabiao.reading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lixiabiao.reading.models.Book;
import com.example.lixiabiao.reading.models.BookLab;
import com.example.lixiabiao.reading.models.Books;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lixiabiao on 16/6/10.
 */
public class SearchBooksFragement extends Fragment {

    private static final String TAG = "BooksFragment";

    private RecyclerView booksRecyclerView;
    private BookLab mBookLab;
    private Call mCall;
    private BooksAdapter mAdapter;

    public static Fragment newInstance() {
        return new SearchBooksFragement();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        booksRecyclerView = (RecyclerView) view.findViewById(R.id.books_recycler_view);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.searchbook_title);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_book, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchItem.expandActionView();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                if (mAdapter != null) {
                    BookLab.get(getActivity()).getSearchBooks().clear();
                    mAdapter.notifyDataSetChanged();
                }
                searchBook(s);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void searchBook(String key) {
        // https://api.douban.com/v2/book/search
        // https://api.douban.com/v2/book/search?q=swift&fields=title,images
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse("https://api.douban.com/v2/book/search").newBuilder();
        builder.addQueryParameter("q", key);
        builder.addQueryParameter("fields", "title,images,author,summary,pages,price,isbn13");
        String url = builder.build().toString();
        Log.d(TAG, url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Books books = gson.fromJson(response.body().charStream(), Books.class);
                BookLab.get(getActivity()).setSearchBooks(books);
                SearchBooksFragement.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Book> books = BookLab.get(getActivity()).getSearchBooks();
                        if (mAdapter == null) {
                            mAdapter = new BooksAdapter(books);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            booksRecyclerView.setLayoutManager(layoutManager);
                            booksRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    private class BooksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView image;
        private TextView author;
        private Button mreadingButton;
        private Button mreadButton;
        private Button mwant_readButton;

        public BooksAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mBookLab = BookLab.get(getActivity());
            title = (TextView) itemView.findViewById(R.id.book_title);
            author = (TextView) itemView.findViewById(R.id.book_author);
            image = (ImageView) itemView.findViewById(R.id.book_image);
            mreadButton = (Button) itemView.findViewById(R.id.read);
            mreadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    Book b = mBookLab.getSearchBooks().get(p);
                    saveBook(b);
                    Log.d(TAG,"1Row for book:" + String.valueOf(getAdapterPosition()));
                }
            });
            mreadingButton = (Button) itemView.findViewById(R.id.reading_button);
            mreadingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    Book b = mBookLab.getSearchBooks().get(p);
                    saveBook(b);
                    Log.d(TAG,"2Row for book:" + String.valueOf(getAdapterPosition()));

                }
            });
            mwant_readButton = (Button) itemView.findViewById(R.id.want_read);
            mwant_readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    Book b = mBookLab.getSearchBooks().get(p);
                    saveBook(b);
                    Log.d(TAG,"3Row for book:" + String.valueOf(getAdapterPosition()));
                }
            });

//        mBookLab = new BookLab();
        }

        private void saveBook(Book book) {
            mBookLab.addBook(book);
        }

        public void bindItem(Book book) {
            String imageUrl = book.getMediumUrl();
            Picasso.with(getActivity()).load(imageUrl).into(image);
            title.setText(book.getTitle());

            author.setText(book.getAuthor());
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition();
            Log.d(TAG,"P =" + p);
            Intent intent = BookDetailActivity.newIntent(getActivity(), p, BookDetailActivity.FROM_SEARCH);
            startActivity(intent);


        }
    }

    private class BooksAdapter extends RecyclerView.Adapter<BooksAdapterViewHolder> {

        private List<Book> books;

        public BooksAdapter(List<Book> books) {
            this.books = books;
        }

        @Override
        public BooksAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_single_book, parent, false);
            BooksAdapterViewHolder viewHolder = new BooksAdapterViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BooksAdapterViewHolder holder, int position) {
            Book b = books.get(position);
            holder.bindItem(b);
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }
}
