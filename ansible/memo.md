ローカルでの実行コマンド

```
# シンタックス チェック
ansible-playbook --connection=local --inventory 127.0.0.1, --limit 127.0.0.1 site.yml -vv --syntax-check

# ドライ ラン
ansible-playbook --connection=local --inventory 127.0.0.1, --limit 127.0.0.1 site.yml -vv --check

# 実行
ansible-playbook --connection=local --inventory 127.0.0.1, --limit 127.0.0.1 site.yml -vv 

# 途中から実行
ansible-playbook --connection=local --inventory 127.0.0.1, --limit 127.0.0.1 site.yml -vv  --start-at="<途中から開始したいタスク名>"
```

リセット用

```
userdel -r vault
groupdel vault
rm -f /usr/local/bin/vault
```
