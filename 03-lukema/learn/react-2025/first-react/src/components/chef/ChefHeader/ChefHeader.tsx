import './ChefHeader.scss';
import ChefLogo from '@/assets/chef/chef-logo.jpg';

export const ChefHeader = () => {
  return (
    <header className="chef-header">
      <link href='https://fonts.googleapis.com/css?family=Inter' rel='stylesheet'></link>
      <img src={ChefLogo} alt="chef logo" />
      <h2>Chef Claude</h2>
    </header>
  );
};
