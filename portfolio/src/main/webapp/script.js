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


// // // navbar
// $(function() {
//     $('a[href="#toggle-search"], .navbar-bootsnipp .bootsnipp-search .input-group-btn > .btn[type="reset"]').on('click', function(event) {
//         event.preventDefault();
//         $('.navbar-bootsnipp .bootsnipp-search .input-group > input').val('');
//         $('.navbar-bootsnipp .bootsnipp-search').toggleClass('open');
//         $('a[href="#toggle-search"]').closest('li').toggleClass('active');

//         if ($('.navbar-bootsnipp .bootsnipp-search').hasClass('open')) {
//             /* I think .focus dosen't like css animations, set timeout to make sure input gets focus */
//             setTimeout(function() { 
//                 $('.navbar-bootsnipp .bootsnipp-search .form-control').focus();
//             }, 100);
//         }           
//     });

//     $(document).on('keyup', function(event) {
//         if (event.which == 27 && $('.navbar-bootsnipp .bootsnipp-search').hasClass('open')) {
//             $('a[href="#toggle-search"]').trigger('click');
//         }
//     });
    
// });

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
