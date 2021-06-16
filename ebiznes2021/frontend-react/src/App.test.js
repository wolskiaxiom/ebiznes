import { render, screen } from '@testing-library/react';
import App from './App';
import {useFetch} from './hooks/useFetch'

test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});

test('try fetch', () => {
  const {response: animals } = useFetch('http://localhost:9000/cats', {})
  console.log(animals);
});