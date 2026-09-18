# Web deploy (docs/)

정적 웹 버전은 `docs/`에 있습니다.

## 영구 배포 (GitHub Pages)

이 저장소에서 Pages를 한 번만 켜면 됩니다.

1. GitHub → **Settings** → **Pages**
2. **Build and deployment** → Source: **GitHub Actions**
3. 이 PR의 `Deploy GitHub Pages` 워크플로를 다시 실행(Re-run)하거나 `main`에 머지

공식 URL:

`https://benjamin5607.github.io/osaka_kokura_trip/`

## 로컬 미리보기

```bash
python3 -m http.server 8765 --directory docs
```
