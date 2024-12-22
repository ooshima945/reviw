// タイトルを動的に変更する関数
function updateTitle(newTitle) {
    document.querySelector('h2').textContent = newTitle;
}



// ユーザーの追加画面を表示
function showUserAdd() {
    hideAll();
    document.getElementById('user-add').classList.remove('hidden');
}

// ユーザーの削除画面を表示
function showUserDelete() {
    hideAll();
            document.getElementById('user-delete').classList.remove('hidden');

}

// 利用状況画面を表示
function showUsageStatus() {
    hideAll();
    document.getElementById('usage-status').classList.remove('hidden');
}
function loadUsageStatusPage(page) {
    fetch(`/usage-status/page?page=${page}`)
        .then(response => response.text())
        .then(html => {
            document.querySelector('#usage-status').innerHTML = html;
        })
        .catch(error => console.error('Error loading usage status page:', error));
}
// すべての画面を非表示にする関数
function hideAll() {
   
    const userAdd = document.getElementById('user-add');
    const userDelete = document.getElementById('user-delete');
    const usageStatus = document.getElementById('usage-status');

  
    if (userAdd) userAdd.classList.add('hidden');
    if (userDelete) userDelete.classList.add('hidden');
    if (usageStatus) usageStatus.classList.add('hidden');
}

