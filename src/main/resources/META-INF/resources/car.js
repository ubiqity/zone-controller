
var lastCommand = 'S'
function executeCommand(command) {
    if (lastCommand == command) {
        return
    }
    lastCommand = command;
    fetch('./rc?command=' + command)
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                response.json().then(function (data) {
                    console.log(data);
                    document.getElementById('logdiv').value = data;
                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        })
}


const handleKeys = (e) => {
console.log(e.key);
    switch (e.key) {
        case 'a':
            executeCommand('L');
            break;
        case 'd':
            executeCommand('R');
            break;
        case 'w':
            executeCommand('F');
            break;
        case 's':
            executeCommand('B');
            break;
        case 'e':
            executeCommand('S');
            break;
    }
};