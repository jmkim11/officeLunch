const form = document.querySelector('#recommendationForm');
const setupView = document.querySelector('#setupView');
const resultView = document.querySelector('#resultView');
const restaurantName = document.querySelector('#restaurantName');
const resultDescription = document.querySelector('#resultDescription');
const retryButton = document.querySelector('#retryButton');
const selectButton = document.querySelector('#selectButton');
const restartButton = document.querySelector('#restartButton');
const message = document.querySelector('#message');

let current = {
  sessionId: null,
  restaurantId: null,
  status: null
};

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  clearMessage();

  const formData = new FormData(form);
  const payload = {
    companyName: formData.get('companyName'),
    category: formData.get('category')
  };

  await runRequest(() => fetch('/api/recommendations', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }), showRecommendation);
});

retryButton.addEventListener('click', async () => {
  clearMessage();
  await runRequest(() => fetch(`/api/recommendations/${current.sessionId}/retry`, {
    method: 'POST'
  }), showRecommendation);
});

selectButton.addEventListener('click', async () => {
  clearMessage();
  await runRequest(() => fetch(`/api/recommendations/${current.sessionId}/selection`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ restaurantId: current.restaurantId })
  }), showSelected);
});

restartButton.addEventListener('click', () => {
  current = { sessionId: null, restaurantId: null, status: null };
  resultView.classList.add('hidden');
  setupView.classList.remove('hidden');
  form.reset();
  clearMessage();
});

async function runRequest(requestFactory, onSuccess) {
  setBusy(true);
  try {
    const response = await requestFactory();
    const data = await parseBody(response);

    if (!response.ok) {
      throw new Error(data.message || '요청을 처리하지 못했습니다.');
    }

    onSuccess(data);
  } catch (error) {
    showMessage(error.message);
  } finally {
    setBusy(false);
  }
}

async function parseBody(response) {
  const text = await response.text();
  return text ? JSON.parse(text) : {};
}

function showRecommendation(data) {
  current = data;
  restaurantName.textContent = data.restaurantName;
  resultDescription.textContent = '마음에 들지 않으면 이전 추천을 제외하고 다시 추천할 수 있습니다.';
  selectButton.disabled = false;
  retryButton.disabled = false;
  setupView.classList.add('hidden');
  resultView.classList.remove('hidden');
}

function showSelected(data) {
  current = data;
  restaurantName.textContent = data.restaurantName;
  resultDescription.textContent = '오늘 점심은 이곳으로 결정했습니다. 맛있게 드세요.';
  selectButton.disabled = true;
  retryButton.disabled = true;
}

function showMessage(text) {
  message.textContent = text;
  message.classList.remove('hidden');
}

function clearMessage() {
  message.textContent = '';
  message.classList.add('hidden');
}

function setBusy(busy) {
  document.querySelectorAll('button').forEach(button => {
    if (button !== restartButton) button.disabled = busy;
  });
}
