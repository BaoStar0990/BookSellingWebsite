const dropdown = document.getElementById("dropdowns");
const danhmuc = document.getElementById("danhmuc");
window.addEventListener("resize", function(e){
    console.log(danhmuc)
    DanhMuc()
})

window.addEventListener("load", function(e){
    console.log(dropdown)
    console.log(danhmuc)
    DanhMuc()
})

function DanhMuc(){
    if(this.window.innerWidth >= 768)
    {
        dropdown.style.transform = `translate(-${danhmuc.getBoundingClientRect().x + 225 - window.innerWidth/2}px, 0)`
        dropdown.classList.add("dropdown-menu-center", "multi-column", "columns-3")
    }
    else{
        dropdown.style.transform = `translate(0,0)`
        dropdown.classList.remove("dropdown-menu-center", "multi-column", "columns-3")
    }
}

function showSearch(e){
    let search_input = document.querySelector(".search-input")
    search_input.classList.remove("invisible")
}