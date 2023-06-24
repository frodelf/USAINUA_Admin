document.getElementById('deleteButton').addEventListener('click', function (event) {
    event.preventDefault();
    if (confirm('Ви впевнені, що хочете видалити цей запис?')) {
        document.getElementById('myForm').submit();
    }
});
