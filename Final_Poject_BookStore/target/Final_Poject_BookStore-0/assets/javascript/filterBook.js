function filterBooks() {

    for(let x of document.getElementsByClassName("bookPriceDiscount")){
        console.log(x.parentNode.parentNode.parentNode.parentNode.style.display);

        if(document.getElementById("discountFilter").value === ""){
            x.parentNode.parentNode.parentNode.parentNode.style.display = "block";
            if((parseInt(x.children[0].innerHTML.trim()) >= document.getElementById("minPrice").value
                    && parseInt(x.children[0].innerHTML.trim()) <= document.getElementById("maxPrice").value)
                || (document.getElementById("minPrice").value === "0" && document.getElementById("maxPrice").value === "0")
                || (document.getElementById("minPrice").value === "" || document.getElementById("maxPrice").value === "")){
                x.parentNode.parentNode.parentNode.parentNode.style.display = "block";
            }
            else {
                x.parentNode.parentNode.parentNode.parentNode.style.display = "none";
            }
        }
        else{
            if((document.getElementById("minPrice").value === ""  || document.getElementById("maxPrice").value === "")
            || (document.getElementById("minPrice").value === "0"  && document.getElementById("maxPrice").value === "0")){
                x.parentNode.parentNode.parentNode.parentNode.style.display = "block";
                if (parseInt(x.children[1].innerHTML.trim()) >= document.getElementById("discountFilter").value){
                    x.parentNode.parentNode.parentNode.parentNode.style.display = "block";
                }
                else {
                    x.parentNode.parentNode.parentNode.parentNode.style.display = "none";
                }
            }
            else{
                x.parentNode.parentNode.parentNode.parentNode.style.display = "block";
                if((parseInt(x.children[0].innerHTML.trim()) >= document.getElementById("minPrice").value
                        && parseInt(x.children[0].innerHTML.trim()) <= document.getElementById("maxPrice").value
                        && parseInt(x.children[1].innerHTML.trim()) >= document.getElementById("discountFilter").value)
                    || (document.getElementById("minPrice").value === "0" && document.getElementById("maxPrice").value === "0" && document.getElementById("discountFilter").value === "0")
                    || (document.getElementById("minPrice").value === ""  || document.getElementById("maxPrice").value === "" || document.getElementById("discountFilter").value === "")){
                    x.parentNode.parentNode.parentNode.parentNode.style.display = "block";
                }
                else {
                    x.parentNode.parentNode.parentNode.parentNode.style.display = "none";
                }
            }
        }
    }
}
