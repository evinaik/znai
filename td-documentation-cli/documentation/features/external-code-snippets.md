# Existing File

To boost documentation maintainability you can refer text and code snippets from already existing files.

    :include-file: file-name.js
    
Include-dash family is our custom extension to markdown to support various scenarios. 
    
:include-file: file-name.js

File will be looked up using following rules:
* directory with a markup file
* root directory of a documentation
* all lookup paths listed in a special file

# Callout Comments

If you already have comments inside your code it would be non effecient to repeat them inside documentation. 
Instead comments can be automatically extracted and presented as part of the text

Given file with inlined comments

:include-file: file-name-with-comments.js

By specifying `commentsType` 
    
    :include-file: file-name-with-comments.js {commentsType: "inline"}

It will be rendered as 

:include-file: file-name-with-comments.js {commentsType: "inline"}