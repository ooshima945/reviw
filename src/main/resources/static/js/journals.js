function redirectToDetail(entryId) {
    window.location.href = '/journal/entry/detail/' + entryId;
}

function updateEntry() {
    const selectedEntryId = document.querySelector('input[name="selectedEntryId"]:checked');
    if (selectedEntryId) {
        const entryId = selectedEntryId.value;
        const updatedEntryData = { entryId: entryId }; // updatedEntryData を entryId のみに変更

        fetch(`/journals/update/${entryId}`, { // URL を修正
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedEntryData)
        })
        .then(response => {
            if (!response.ok) {
                console.error('更新失敗', response);
                alert('更新に失敗しました。');
            } else {
                alert('更新に成功しました。'); // メッセージを変更
                location.reload();
            }
        })
        .catch(error => {
            console.error('更新エラー', error);
            alert('更新中にエラーが発生しました。');
        });
    } else {
        alert('更新する仕訳を選択してください。'); // メッセージを変更
    }
}

async function handleCreate(event) {
    event.preventDefault(); // フォームのデフォルトの送信動作を阻止

    const formData = new FormData(document.getElementById('createJournalForm'));
    const data = {};
    for (const [key, value] of formData.entries()) {
        data[key] = value;
    }

    const response = await fetch('/journalentry/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        const errorData = await response.json();
        console.error('Error creating journal entry:', errorData.message);
        alert('仕訳登録に失敗しました: ' + errorData.message);
    } else {
        console.log('Journal entry created successfully!');
        alert('仕訳登録が成功しました!');
        // 必要に応じてページをリロードするなど
        location.reload();
    }
}