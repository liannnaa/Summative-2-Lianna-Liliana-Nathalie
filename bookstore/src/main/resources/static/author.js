const apiUrl = 'http://localhost:8080/author';

async function addAuthor() {
    const author = {
        firstName: document.getElementById('addFirstName').value,
        lastName: document.getElementById('addLastName').value,
        street: document.getElementById('addStreet').value,
        city: document.getElementById('addCity').value,
        state: document.getElementById('addState').value,
        postalCode: document.getElementById('addPostalCode').value,
        phone: document.getElementById('addPhone').value,
        email: document.getElementById('addEmail').value
    };
    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(author),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('addAuthorStatus').textContent = 'Author added successfully.';
        } catch (error) {
            document.getElementById('addAuthorStatus').textContent = 'Error: ' + error;
        }
    }

async function updateAuthor() {
    const author = {
        authorId: document.getElementById('updateAuthorId').value,
        firstName: document.getElementById('updateFirstName').value,
        lastName: document.getElementById('updateLastName').value,
        street: document.getElementById('updateStreet').value,
        city: document.getElementById('updateCity').value,
        state: document.getElementById('updateState').value,
        postalCode: document.getElementById('updatePostalCode').value,
        phone: document.getElementById('updatePhone').value,
        email: document.getElementById('updateEmail').value
    };
    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(author),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('updateAuthorStatus').textContent = 'Author updated successfully.';
        } catch (error) {
            document.getElementById('updateAuthorStatus').textContent = 'Error: ' + error;
        }
    }

async function deleteAuthor() {
    const authorId = document.getElementById('deleteAuthorId').value;
    try {
        const response = await fetch(`${apiUrl}/${authorId}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('deleteAuthorStatus').textContent = 'Author deleted successfully.';
        } catch (error) {
            document.getElementById('deleteAuthorStatus').textContent = 'Error: ' + error;
        }
    }

async function getAuthors() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error("An error occurred while getting the authors");
        }
        const authors = await response.json();
        let output = '';
        for (let author of authors) {
            output += `<p class="message-output">Author Id: ${author.authorId}</p>`;
            output += `<p class="message-output">First Name: ${author.firstName}</p>`;
            output += `<p class="message-output">Last Name: ${author.lastName}</p>`;
            output += `<hr>`;
        }

        document.getElementById('authorsOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('authorsOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}

async function getAuthorById() {
    try {
        const authorId = document.getElementById('getAuthorId').value;
        const response = await fetch('http://localhost:8080/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({query: `{
              getAuthorById(id: "${authorId}") {
                authorId
                firstName
                lastName
                street
                city
                state
                postalCode
                phone
                email
                books {
                    bookId
                    title
                    publisher {
                        publisherId
                        name
                    }
                }
              }
            }`}),
        });

        if (!response.ok) {
            throw new Error("An error occurred while getting the author by id");
        }

        const data = await response.json();
        let author = data.data.getAuthorById;

        let output = '';
        output += `<p class="message-output">Author Id: ${author.authorId}</p>`;
        output += `<p class="message-output">First Name: ${author.firstName}</p>`;
        output += `<p class="message-output">Last Name: ${author.lastName}</p>`;
        output += `<p class="message-output">Street: ${author.street}</p>`;
        output += `<p class="message-output">City: ${author.city}</p>`;
        output += `<p class="message-output">State: ${author.state}</p>`;
        output += `<p class="message-output">Postal Code: ${author.postalCode}</p>`;
        output += `<p class="message-output">Phone: ${author.phone}</p>`;
        output += `<p class="message-output">Email: ${author.email}</p>`;
        output += `<hr>`;

        document.getElementById('authorsOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('authorsOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}