$(document).ready(function() {
    $(".minusButton").on("click", function (evt){
        evt.preventDefault();

        let quantityInput = $("#quantity");
        console.log(parseInt(quantityInput.val()));
        let newQuantity = parseInt(quantityInput.val()) - 1;
        if(newQuantity > 0) quantityInput.val(newQuantity);
    });

    $(".plusButton").on("click", function (evt){
        evt.preventDefault();
         let count = $(this).attr("pid");
        let quantityInput = $("#quantity");

        let newQuantity = parseInt(quantityInput.val()) + 1;
        if(newQuantity <= count) quantityInput.val(newQuantity);
    });
});


// $(document).ready(function() {
//     $(".minusButton").on("click", function (evt){
//         evt.preventDefault();
//
//         let quantityInput = $("#quantity");
//         console.log(parseInt(quantityInput.val()));
//         let newQuantity = parseInt(quantityInput.val()) - 1;
//         if(newQuantity > 0) quantityInput.val(newQuantity);
//     });
//
//     $(".plusButton").on("click", function (evt){
//         evt.preventDefault();
//         let count = $(this).attr("pid");
//         let quantityInput = $("#quantity");
//
//         let newQuantity = parseInt(quantityInput.val()) + 1;
//         if(newQuantity <= count) quantityInput.val(newQuantity);
//     });
// });