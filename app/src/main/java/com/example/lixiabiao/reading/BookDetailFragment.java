package com.example.lixiabiao.reading;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lixiabiao.reading.models.Book;
import com.example.lixiabiao.reading.models.BookLab;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFragment extends Fragment {
    private static final String TAG = "BookDeatil";
    private BookLab mBookLab;
    private ImageView image;
    private TextView title;
    private TextView author;
    private TextView summary;
    private TextView price;
    private TextView pages;
    private Button mreadingButton;
    private Button mreadButton;
    private Button mwant_readButton;
    private TextView status;

    public static BookDetailFragment newInstance() {
        return new BookDetailFragment();
    }


    public BookDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int p = getActivity().getIntent().getIntExtra(BookDetailActivity.EXTRA_BOOK, 0);
        String from = getActivity().getIntent().getStringExtra(BookDetailActivity.FROM);

        Log.d(TAG, "POO =" + p);
        View v = inflater.inflate(R.layout.fragment_book_detail, container, false);
        mBookLab = BookLab.get(getActivity());
        List<Book> books;
        if (from.equals(BookDetailActivity.FROM_LOCAL)) {
            books = mBookLab.getLocalBooks();
        } else {
            books = mBookLab.getSearchBooks();
        }
        final Book b = books.get(p);
        image = (ImageView) v.findViewById(R.id.book_image);
        String url = b.getMediumUrl();
        Picasso.with(getActivity()).load(url).into(image);
        title = (TextView) v.findViewById(R.id.book_title);
        title.setText(b.getTitle());
        author = (TextView) v.findViewById(R.id.book_author);
        author.setText(b.getAuthor());
        status = (TextView) v.findViewById(R.id._book_status);
        updataBookStatus(b);
        summary = (TextView) v.findViewById(R.id.book_summary);
        summary.setText(b.getSummary());
        pages = (TextView) v.findViewById(R.id.book_pages);
        pages.setText(b.getPages());
        price = (TextView) v.findViewById(R.id.book_price);
        price.setText(b.getPrice());
        mreadButton = (Button) v.findViewById(R.id.read);
        mreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBookStatus(v, b);
                saveBook(b);
                updataBookStatus(b);
            }
        });
        mreadingButton = (Button) v.findViewById(R.id.reading_button);
        mreadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBookStatus(v, b);
                saveBook(b);
                updataBookStatus(b);
            }
        });
        mwant_readButton = (Button) v.findViewById(R.id.want_read);
        mwant_readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBookStatus(v, b);
                saveBook(b);
                updataBookStatus(b);
            }
        });
        return v;
    }

    private void updataBookStatus(Book b) {
        status.setText(b.getStatus());
    }

    private void setBookStatus(View v, Book b) {
        Button button = (Button) v;
        String title = (String) button.getText();
        b.setStatus(title);
        Log.d(TAG,"SetTitle:" + title + b.toString());
    }

    private void saveBook(Book book) {
        mBookLab.addBook(book);

    }

}
