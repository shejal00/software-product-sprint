// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Returns comment form only if the user is logged in.
 */
async function toggleForm(){
    await getComments();
    const response = await fetch('/authentication');
    const userResponse = await response.json();
    const commentForm = document.getElementById('form-container');
    const logoutContainer = document.getElementById('logout-container');
    const loginContainer = document.getElementById('login-container');
    const loginUrl = document.getElementById('login-url');
    const logoutUrl = document.getElementById('logout-url');

    if (userResponse.checkLoggedIn) {
        loginContainer.hidden = true;
        logoutUrl.href = userResponse.redirectToThisUrl;
    } else {
        commentForm.hidden = true;
        logoutContainer.hidden = true;
        loginUrl.href = userResponse.redirectToThisUrl;
    }
}

/**
 * Returns comments in the form of list.
 */
function getComments() {
    fetch('/comments').then(response => response.json()).then((comments) => {
    const commentListElement = document.getElementById('message-container');
    for (let i = 0; i <comments.length; i++) {
        commentListElement.appendChild(
        createCommentElement(comments[i].message, comments[i].email)
        );
    }
  });
}

/** Creates a comment. */
function createCommentElement(message, email) {
    const comment = createDivElement();
    comment.append(createListElement(email));
    comment.append(createParagraphElement(message));
    return comment;
}

/** Creates an <li> element containing text. */
function createListElement(text) {
    const liElement = document.createElement('li');
    liElement.innerText = text;
    return liElement;
}

/** Creates an <p> element containing text. */
function createParagraphElement(text) {
    const paragraphElement = document.createElement('p');
    paragraphElement.innerText = text;
    return paragraphElement;
}

/** Creates an <div> element containing text. */
function createDivElement(text) {
    return document.createElement('div');
}

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
    const greetings =
        ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

    // Pick a random greeting.
    const greeting = greetings[Math.floor(Math.random() * greetings.length)];

    // Add it to the page.
    const greetingContainer = document.getElementById('greeting-container');
    greetingContainer.innerText = greeting;
}
