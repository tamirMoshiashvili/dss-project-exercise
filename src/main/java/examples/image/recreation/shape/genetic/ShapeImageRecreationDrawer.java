package examples.image.recreation.shape.genetic;

import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeSpecification;
import optimization.genetic.GeneticAlgorithm;
import optimization.genetic.listener.GenerationListener;
import optimization.genetic.selection.Selector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ShapeImageRecreationDrawer implements GenerationListener<ImageRecreation<ShapeSpecification>> {
	private BufferedImage targetImage;
	private JLabel generationDataLabel;
	private ImageIcon imageIcon;
	private JPanel predictedImagePanel;

	public ShapeImageRecreationDrawer(BufferedImage targetImage) {
		this.targetImage = targetImage;
		this.generationDataLabel = new JLabel();
		this.imageIcon = new ImageIcon();
		this.predictedImagePanel = createPredictedImagePanel();

		initializeWindow();
	}

	private JPanel createPredictedImagePanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel(imageIcon));
		return panel;
	}

	private void initializeWindow() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(50 + 2 * targetImage.getWidth(), 80 + targetImage.getHeight()));
		frame.add(createMainPanel());
		frame.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);

		mainPanel.add(createGenerationNumberPanel());
		mainPanel.add(createImagesPanel());
		return mainPanel;
	}

	private JPanel createGenerationNumberPanel() {
		JPanel generationPanel = new JPanel();
		generationPanel.setLayout(new GridBagLayout());

		generationPanel.add(generationDataLabel);
		return generationPanel;
	}

	private JPanel createImagesPanel() {
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(layout);

		mainPanel.add(createTargetImagePanel());
		mainPanel.add(predictedImagePanel);
		return mainPanel;
	}

	private JPanel createTargetImagePanel() {
		JPanel targetImagePanel = new JPanel();
		targetImagePanel.add(new JLabel(new ImageIcon(targetImage)));
		return targetImagePanel;
	}

	@Override
	public void notifyGenerationStarted() {
	}

	@Override
	public void notifyGenerationEnded(GeneticAlgorithm<ImageRecreation<ShapeSpecification>> geneticAlgorithm,
									  Selector<ImageRecreation<ShapeSpecification>> selector) {
		ImageRecreation<ShapeSpecification> imageRecreation = selector.getFittest();
		BufferedImage recreatedImage = imageRecreation.recreateImage(targetImage.getWidth(), targetImage.getHeight());

		imageIcon.setImage(recreatedImage);
		generationDataLabel.setText(String.format("Generation: %d | Num Shapes: %d",
				geneticAlgorithm.getGeneration(), imageRecreation.getItems().size()));

		repaint(predictedImagePanel);
		repaint(generationDataLabel);
	}

	private void repaint(JComponent component) {
		component.revalidate();
		component.repaint();
	}
}
