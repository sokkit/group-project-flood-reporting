console.log("script loaded")
// sort table taken from https://www.w3schools.com/howto/howto_js_sort_table.asp
function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("reports-table");
    var sortButton = document.getElementById("dateSortButton");
    if (sortButton.getAttribute("class") == "fas fa-chevron-up") {
        sortButton.setAttribute("class", "fas fa-chevron-down")
    } else {
        sortButton.setAttribute("class", "fas fa-chevron-up")
    }
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}
// end of reference

function clearDate() {
    document.getElementById("dateFrom").value = "";
    document.getElementById("dateTo").value = "";
}

function hideValidated(){
    const validatedTable = document.getElementsByClassName('validated-reports');
    const unvalidatedTable = document.getElementsByClassName('unvalidated-reports');
    for (let i = 0; i < validatedTable.length; i++) {
        validatedTable.item(i).style.display = 'none';
    }
    for (let i = 0; i < unvalidatedTable.length; i++) {
        unvalidatedTable.item(i).style.display = 'table-row';
    }
}
function hideUnvalidated(){
    const validatedTable = document.getElementsByClassName('validated-reports');
    const unvalidatedTable = document.getElementsByClassName('unvalidated-reports');
    for (let i = 0; i < unvalidatedTable.length; i++) {
        unvalidatedTable.item(i).style.display = 'none';
    }
    for (let i = 0; i < validatedTable.length; i++) {
        validatedTable.item(i).style.display = 'table-row';
    }
    // unvalidatedTable.style.display = 'none'
}
function hideNone(){
    const validatedTable = document.getElementsByClassName('validated-reports');
    const unvalidatedTable = document.getElementsByClassName('unvalidated-reports');
    for (let i = 0; i < validatedTable.length; i++) {
        validatedTable.item(i).style.display = 'table-row';
    }
    for (let i = 0; i < unvalidatedTable.length; i++) {
        unvalidatedTable.item(i).style.display = 'table-row';
    }
}

function processCheckbox(){

    const queryString = window.location.search;
    const params = new URLSearchParams(queryString);
    const showUnreviewed = params.get("unreviewed");
    const checkBox = document.getElementById("unreviewed-check")
    if (showUnreviewed == 'false'){
        checkBox.checked = false
        hideUnvalidated()
    }
    else{
        checkBox.checked = true;
        hideValidated()
    }
}

function processDropdown(){
    const params = new URLSearchParams(window.location.search);
    const display = params.get("display");
    console.log(display + "Thisworks")
    document.getElementById("reviewedType").value = display;
    if (display == "All"){
        hideNone()
        document.getElementById("latest-report-text").innerText = "All Reports"
    }
    if (display == "Unreviewed"){
        hideValidated()
        document.getElementById("latest-report-text").innerText = "Unreviewed"
    }
    if(display == "Reviewed"){
        hideUnvalidated()
        document.getElementById("latest-report-text").innerText = "Reviewed"
    }

}

function dropdownCheck(){

    window.location="/reports-overview?display="+document.getElementById("reviewedType").value
}

function checkboxCheck(){
    window.location = "/reports-overview?unreviewed="+document.getElementById("unreviewed-check").checked


}



