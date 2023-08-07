const apiUrl = 'http://localhost:8080/publisher';

async function addPublisher() {
    const publisher = {
        name: document.getElementById('addName').value,
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
            body: JSON.stringify(publisher),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('addPublisherStatus').textContent = 'Publisher added successfully.';
        } catch (error) {
            document.getElementById('addPublisherStatus').textContent = 'Error: ' + error;
        }
    }

async function updatePublisher() {
    const publisher = {
        publisherId: document.getElementById('updatePublisherId').value,
        name: document.getElementById('updateName').value,
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
            body: JSON.stringify(publisher),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('updatePublisherStatus').textContent = 'Publisher updated successfully.';
        } catch (error) {
            document.getElementById('updatePublisherStatus').textContent = 'Error: ' + error;
        }
    }

async function deletePublisher() {
    const publisherId = document.getElementById('deletePublisherId').value;
    try {
        const response = await fetch(`${apiUrl}/${publisherId}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        document.getElementById('deletePublisherStatus').textContent = 'Publisher deleted successfully.';
        } catch (error) {
            document.getElementById('deletePublisherStatus').textContent = 'Error: ' + error;
        }
    }

async function getPublishers() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error("An error occurred while getting the publishers");
        }
        const publishers = await response.json();
        let output = '';
        for (let publisher of publishers) {
            output += `<p class="message-output">Publisher Id: ${publisher.publisherId}</p>`;
            output += `<p class="message-output">Name: ${publisher.name}</p>`;
            output += `<hr>`;
        }

        document.getElementById('publishersOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('publishersOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}

async function getPublisherById() {
    try {
        const publisherId = document.getElementById('getPublisherId').value;
        const response = await fetch('http://localhost:8080/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({query: `{
              getPublisherById(id: "${publisherId}") {
                publisherId
                name
                street
                city
                state
                postalCode
                phone
                email
                publisherBooks {
                    title
                }
              }
            }`}),
        });

        if (!response.ok) {
            throw new Error("An error occurred while getting the publisher by id");
        }

        const data = await response.json();
        let publisher = data.data.getPublisherById;

        let output = '';
        output += `<p class="message-output">Publisher Id: ${publisher.publisherId}</p>`;
        output += `<p class="message-output">Name: ${publisher.name}</p>`;
        output += `<p class="message-output">Street: ${publisher.street}</p>`;
        output += `<p class="message-output">City: ${publisher.city}</p>`;
        output += `<p class="message-output">State: ${publisher.state}</p>`;
        output += `<p class="message-output">Postal Code: ${publisher.postalCode}</p>`;
        output += `<p class="message-output">Phone: ${publisher.phone}</p>`;
        output += `<p class="message-output">Email: ${publisher.email}</p>`;
        output += `<hr>`;

        document.getElementById('publishersOutput').innerHTML = output;
    } catch (error) {
        document.getElementById('publishersOutput').innerHTML = `<p>Error: ${error.message}</p>`;
    }
}