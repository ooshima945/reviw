// タイトルを動的に変更する関数
function updateTitle(newTitle) {
    document.querySelector('h2').textContent = newTitle;
}

// 機能の追加画面を表示
function showFeatureAdd() {
    hideAll();
    document.getElementById('feature-add').classList.remove('hidden');
}

// 機能の削除画面を表示
function showFeatureDelete() {
    hideAll();
    document.getElementById('feature-delete').classList.remove('hidden');
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

// すべての画面を非表示にする関数
function hideAll() {
    const featureAdd = document.getElementById('feature-add');
    const featureDelete = document.getElementById('feature-delete');
    const userAdd = document.getElementById('user-add');
    const userDelete = document.getElementById('user-delete');
    const usageStatus = document.getElementById('usage-status');

    if (featureAdd) featureAdd.classList.add('hidden');
    if (featureDelete) featureDelete.classList.add('hidden');
    if (userAdd) userAdd.classList.add('hidden');
    if (userDelete) userDelete.classList.add('hidden');
    if (usageStatus) usageStatus.classList.add('hidden');
}

