package com.example.SpringDemo.ServieImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Book;
import com.example.SpringDemo.Entity.Publisher;
import com.example.SpringDemo.ExceptionClasses.PublisherNotFoundException;
import com.example.SpringDemo.Repository.PublisherRepository;
import com.example.SpringDemo.RequestDTO.BookReqForPublisher;
import com.example.SpringDemo.RequestDTO.PublisherRequest;
import com.example.SpringDemo.ResponseDTO.PublisherResponse;
import com.example.SpringDemo.Service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public PublisherResponse createPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = new Publisher();
        publisher.setPName(publisherRequest.getPName());

        if (publisherRequest.getBooks() != null) {
            List<Book> books = new ArrayList<>();

            for (BookReqForPublisher bookRequest : publisherRequest.getBooks()) {
                Book book = new Book();
                book.setTitle(bookRequest.getTitle());
                book.setAvailableCopies(bookRequest.getAvailableCopies());

                book.setPublisher(publisher);


                books.add(book);
            }

            publisher.setBooks(books);
        }

        publisher = publisherRepository.save(publisher);


        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setPublisherId(publisher.getPublisherId());
        publisherResponse.setPName(publisher.getPName());

        List<BookReqForPublisher> bookRequests = new ArrayList<>();
        for (Book book : publisher.getBooks()) {
            BookReqForPublisher bookRequest = new BookReqForPublisher();
            bookRequest.setBookId(book.getBookId());
            bookRequest.setTitle(book.getTitle());
            bookRequest.setAvailableCopies(book.getAvailableCopies());
            bookRequests.add(bookRequest);
        }
        publisherResponse.setBooks(bookRequests);

        return publisherResponse;
    }

    @Override
    public PublisherResponse getPublisherById(Integer publisherId) {
        Optional<Publisher> publisherOpt = publisherRepository.findById(publisherId);
        Publisher publisher = publisherOpt.orElseThrow(() -> 
            new PublisherNotFoundException("Publisher not found with ID: " + publisherId));

        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setPublisherId(publisher.getPublisherId());
        publisherResponse.setPName(publisher.getPName());

        List<BookReqForPublisher> bookRequests = new ArrayList<>();
        for (Book book : publisher.getBooks()) {
            BookReqForPublisher bookRequest = new BookReqForPublisher();
            bookRequest.setBookId(book.getBookId());
            bookRequest.setTitle(book.getTitle());
            bookRequest.setAvailableCopies(book.getAvailableCopies());
            bookRequests.add(bookRequest);
        }
        publisherResponse.setBooks(bookRequests);

        return publisherResponse;
    }

    @Override
    public List<PublisherResponse> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        List<PublisherResponse> publisherResponses = new ArrayList<>();

        for (Publisher publisher : publishers) {
            PublisherResponse publisherResponse = new PublisherResponse();
            publisherResponse.setPublisherId(publisher.getPublisherId());
            publisherResponse.setPName(publisher.getPName());

            List<BookReqForPublisher> bookRequests = new ArrayList<>();
            for (Book book : publisher.getBooks()) {
                BookReqForPublisher bookRequest = new BookReqForPublisher();
                bookRequest.setBookId(book.getBookId());
                bookRequest.setTitle(book.getTitle());
                bookRequest.setAvailableCopies(book.getAvailableCopies());
                bookRequests.add(bookRequest);
            }
            publisherResponse.setBooks(bookRequests);

            publisherResponses.add(publisherResponse);
        }

        return publisherResponses;
    }

    @Override
    public PublisherResponse updatePublisher(PublisherRequest publisherRequest) {
        Publisher existingPublisher = new Publisher();

        existingPublisher.setPublisherId(publisherRequest.getPublisherId());
        existingPublisher.setPName(publisherRequest.getPName());

        if (publisherRequest.getBooks() != null && !publisherRequest.getBooks().isEmpty()) {
            List<Book> books = new ArrayList<>();
            for (BookReqForPublisher bookRequest : publisherRequest.getBooks()) {
                Book book = new Book();
                book.setTitle(bookRequest.getTitle());
                book.setAvailableCopies(bookRequest.getAvailableCopies());
                book.setPublisher(existingPublisher);  // Set the existing Publisher instance
                books.add(book);
            }

            existingPublisher.setBooks(books);
        }

        Publisher updatedPublisher = publisherRepository.save(existingPublisher);

        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setPublisherId(updatedPublisher.getPublisherId());
        publisherResponse.setPName(updatedPublisher.getPName());

        List<BookReqForPublisher> bookRequests = new ArrayList<>();
        for (Book book : updatedPublisher.getBooks()) {
            BookReqForPublisher bookRequest = new BookReqForPublisher();
            bookRequest.setBookId(book.getBookId());
            bookRequest.setTitle(book.getTitle());
            bookRequest.setAvailableCopies(book.getAvailableCopies());
            bookRequests.add(bookRequest);
        }
        publisherResponse.setBooks(bookRequests);

        return publisherResponse;
    }

    @Override
    public void deletePublisher(Integer publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with ID: " + publisherId));
        
        publisherRepository.delete(publisher);
    }


}
