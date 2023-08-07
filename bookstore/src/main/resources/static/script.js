const apiUrl = 'http://localhost:8080/books';

async function addBook() {
    const book = {
        isbn: document.getElementById('addISBN').value,
        publishDate: document.getElementById('addPublishDate').value,
        author: {
            authorId: document.getElementById('addAuthorId').value
        },
        title: document.getElementById('addTitle').value,
        publisher: {
            publisherId: document.getElementById('addPublisherId').value
        },
        price: document.getElementById('addPrice').value
    };
    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(book),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('addBookStatus').textContent = 'Book added successfully.';
        } catch (error) {
            document.getElementById('addBookStatus').textContent = 'Error: ' + error;
        }
    }

async function updateBook() {
    const book = {
        bookId: document.getElementById('updateBookId').value,
        isbn: document.getElementById('updateISBN').value,
        publishDate: document.getElementById('updatePublishDate').value,
        author: {
            authorId: document.getElementById('updateAuthorId').value
        },
        title: document.getElementById('updateTitle').value,
        publisher: {
            publisherId: document.getElementById('updatePublisherId').value
        },
        price: document.getElementById('updatePrice').value
    };
    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(book),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('updateBookStatus').textContent = 'Book updated successfully.';
        } catch (error) {
            document.getElementById('updateBookStatus').textContent = 'Error: ' + error;
        }
    }

async function deleteBook() {
    const bookId = document.getElementById('deleteBookId').value;
    try {
        const response = await fetch(`${apiUrl}/${bookId}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('deleteBookStatus').textContent = 'Book deleted successfully.';
        } catch (error) {
            document.getElementById('deleteBookStatus').textContent = 'Error: ' + error;
        }
    }

async function getBooks() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error("An error occurred while getting the books");
        }
        const books = await response.json();
        let output = '';
        for (let book of books) {
            output += `<p class="message-output">Book Id: ${book.bookId}</p>`;
            output += `<p class="message-output">Title: ${book.title}</p>`;
            output += `<p class="message-output">Author: ${book.author.firstName} ${book.author.lastName}</p>`;
            output += `<p class="message-output">Publisher: ${book.publisher.name}</p>`;
            output += `<hr>`;
        }

        document.getElementById('booksOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('booksOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}

async function getBookById() {
    try {
        const bookId = document.getElementById('getBookId').value;
        const response = await fetch('http://localhost:8080/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({query: `{
              findBookById(id: "${bookId}") {
                bookId
                isbn
                publishDate
                title
                price
                author {
                  authorId
                  firstName
                  lastName
                }
                publisher {
                  publisherId
                  name
                }
              }
            }`}),
        });

        if (!response.ok) {
            throw new Error("An error occurred while getting the book by id");
        }

        const data = await response.json();
        let book = data.data.findBookById;

        let output = '';
        output += `<p class="message-output">Book Id: ${book.bookId}</p>`;
        output += `<p class="message-output">Isbn: ${book.isbn}</p>`;
        output += `<p class="message-output">Price: ${book.price}</p>`;
        output += `<p class="message-output">Publish Date: ${book.publishDate}</p>`;
        output += `<p class="message-output">Title: ${book.title}</p>`;
        output += `<p class="message-output">Author Id: ${book.author.authorId}</p>`;
        output += `<p class="message-output">Author: ${book.author.firstName} ${book.author.lastName}</p>`;
        output += `<p class="message-output">Publisher Id: ${book.publisher.publisherId}</p>`;
        output += `<p class="message-output">Publisher: ${book.publisher.name}</p>`;
        output += `<hr>`;

        document.getElementById('booksOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('booksOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}

async function getBooksByAuthorId() {
    try {
        const authorId = document.getElementById('getAuthorId').value;
        const response = await fetch('http://localhost:8080/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({query: `{
              findBooksByAuthorId(authorId: "${authorId}") {
                bookId
                isbn
                publishDate
                title
                price
                author {
                  authorId
                  firstName
                  lastName
                }
                publisher {
                  publisherId
                  name
                }
              }
            }`}),
        });

        if (!response.ok) {
            throw new Error("An error occurred while getting the books by author id");
        }

        const data = await response.json();
        let books = data.data.findBooksByAuthorId;

        let output = '';
        for (let book of books) {
            output += `<p class="message-output">Book Id: ${book.bookId}</p>`;
            output += `<p class="message-output">Title: ${book.title}</p>`;
            output += `<p class="message-output">Author: ${book.author.firstName} ${book.author.lastName}</p>`;
            output += `<p class="message-output">Publisher: ${book.publisher.name}</p>`;
            output += `<hr>`;
        }

        document.getElementById('booksOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('booksOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}
