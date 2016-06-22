package com.example.lixiabiao.reading;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.lixiabiao.reading.R.id.books_recycler_view;
import static com.example.lixiabiao.reading.R.id.fragment_single_book;

/**
 * Created by lixiabiao on 16/6/12.
 */
public class BooksFragement extends Fragment {
    private static final String TAG = "BooksFragment";
    private RecyclerView booksRecyclerView;
    private BookLab mBookLab;
    private  BooksAdapter adapter;
    private List<Book> mBooks;

    public static Fragment newInstance() {
        return new BooksFragement();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        getActivity().setTitle(R.string.books_title);
        booksRecyclerView = (RecyclerView) view.findViewById(books_recycler_view);
        mBookLab = BookLab.get(getActivity());
        setHasOptionsMenu(true);
        mBooks = BookLab.get(getActivity()).getLocalBooks();
        adapter = new BooksAdapter(mBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        booksRecyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                BooksAdapterViewHolder vh = (BooksAdapterViewHolder) viewHolder;
                Book book = vh.getBook();
                mBooks.remove(book);
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                mBookLab.delete(book);

                Log.d(TAG,"I Had Swiped");
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        booksRecyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(booksRecyclerView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            for (Book book : mBooks) {
                Log.d(TAG,"REsumeTITLE:" + book.getTitle() + "ResumeSTATUS:" + book.getStatus());

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_book_list, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Intent i = SearchBooksActivity.newIntent(getActivity());
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class BooksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView image;
        private TextView author;
        private Button mreadingButton;
        private Button mreadButton;
        private Button mwant_readButton;
        private Book book;
        private TextView status;

        public BooksAdapterViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.book_title);
            author = (TextView) itemView.findViewById(R.id.book_author);
            image = (ImageView) itemView.findViewById(R.id.book_image);
            status = (TextView) itemView.findViewById(R.id._book_status);
            mreadButton = (Button) itemView.findViewById(R.id.read);
            mreadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"1Row for book:" + String.valueOf(getAdapterPosition()));
                   setBookStatus(v, book);
                    updataBookStatus(book);
                }
            });
            mreadingButton = (Button) itemView.findViewById(R.id.reading_button);
            mreadingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"2Row for book:" + String.valueOf(getAdapterPosition()));
                    setBookStatus(v, book);
                    updataBookStatus(book);

                }
            });
            mwant_readButton = (Button) itemView.findViewById(R.id.want_read);
            mwant_readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = getBook();
                    setBookStatus(v, book);
                    updataBookStatus(book);
                    Log.d(TAG,"3Row for book:" + String.valueOf(getAdapterPosition()));

                }
            });

//        mBookLab = new BookLab();
        }

        private void setBookStatus(View v, Book b) {

            Button button = (Button) v;
            String title = (String) button.getText();
            b.setStatus(title);
            Log.d(TAG, "SetTitle:" +title + b.toString());
        }

        private void updataBookStatus(Book b) {
            Log.d(TAG,"I did");
            Log.d(TAG,"TITLE:" + book.getTitle() + "STATUS:" + book.getStatus());
            status.setText(b.getStatus());
        }

        public void bindItem(Book book) {
            this.book = book;
            title.setText(book.getTitle());
            Log.d(TAG,"URL = " + book.getImageUrl());
            author.setText(book.getAuthor());
            String imageUrl = book.getMediumUrl();
            Picasso.with(getActivity()).load(imageUrl).into(image);
            updataBookStatus(book);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition();
            Log.d(TAG,"P =" + p);
            Intent intent = BookDetailActivity.newIntent(getActivity(), p, BookDetailActivity.FROM_LOCAL);
            startActivity(intent);

        }

        public Book getBook() {
            return book;
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
