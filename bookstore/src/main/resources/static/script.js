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
    await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(book),
    });
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
    await fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(book),
    });
}

async function deleteBook() {
    const bookId = document.getElementById('deleteBookId').value;
    await fetch(`${apiUrl}/${bookId}`, {
        method: 'DELETE',
    });
}

async function getBooks() {
    const response = await fetch(apiUrl);
    const books = await response.json();

    let output = '';
    for (let book of books) {
        output += `<p>Title: ${book.title}</p>`;
        output += `<p>Author: ${book.author.firstName} ${book.author.lastName}</p>`;
        output += `<p>Price: ${book.price}</p>`;
        output += `<hr>`;
    }

    document.getElementById('booksOutput').innerHTML = output;
}

async function getBookById() {
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

    const data = await response.json();
    let book = data.data.findBookById;

    let output = '';
    output += `<p>Title: ${book.title}</p>`;
    output += `<p>Author: ${book.author.firstName} ${book.author.lastName}</p>`;
    output += `<p>Price: ${book.price}</p>`;

    document.getElementById('booksOutput').innerHTML = output;
}

async function getBooksByAuthorId() {
    const authorId = document.getElementById('getAuthorId').value;
    const response = await fetch('http://localhost:8080/graphql', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({query: `{
          findBooksByAuthorId(authorId: "${authorId}") {
            bookId
            title
            author {
              firstName
              lastName
            }
          }
        }`}),
    });

    const data = await response.json();
    let books = data.data.findBooksByAuthorId;

    let output = '';
    for (let book of books) {
        output += `<p>Title: ${book.title}</p>`;
        output += `<p>Author: ${book.author.firstName} ${book.author.lastName}</p>`;
        output += `<hr>`;
    }

    document.getElementById('booksOutput').innerHTML = output;
}
